package com.sofka.tourdefrance.routers;

import com.sofka.tourdefrance.models.CyclingTeamDTO;
import com.sofka.tourdefrance.models.CyclistDTO;
import com.sofka.tourdefrance.usecases.cyclistusecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CyclistRouter {

    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "getAllCyclists",
            summary = "Get all cyclists",
            tags = {"Cyclist"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CyclistDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> getAllCyclists(GetCyclistsUseCase useCase) {
        return route(GET("/cyclists").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), CyclistDTO.class))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "createCyclist",
            summary = "Create a cyclist",
            tags = {"Cyclist"},
            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = CyclistDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CyclistDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
            }))
    public RouterFunction<ServerResponse> createCyclist(CreateCyclistUseCase useCase) {
        Function<CyclistDTO, Mono<ServerResponse>> executor = cyclistDTO -> useCase.apply(cyclistDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result))
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(String.format(
                                "Error: A Cyclist already exists with this number (%s)", cyclistDTO.getCompetitorNumber()
                        )));

        return route(POST("/cyclists").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclistDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue("Error: " + throwable.getMessage()))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "updateCyclistById",
            summary = "Update a cyclist by id",
            tags = {"Cyclist"},
            parameters = @Parameter(name = "id", in = ParameterIn.PATH, description = "Id of cyclist to update", required = true),
            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = CyclistDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CyclistDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }))
    public RouterFunction<ServerResponse> updateCyclistById(UpdateCyclistUseCase useCase) {
        Function<String, Function<CyclistDTO, Mono<ServerResponse>>> executor = id -> cyclistDTO -> useCase.apply(id, cyclistDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result))
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(e.getMessage()));
        return route(
                PUT("cyclists/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclistDTO.class).flatMap(executor.apply(request.pathVariable("id")))
                        .onErrorResume(throwable -> ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Error: " + throwable.getMessage()))

        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "getCyclistsByCountry",
            summary = "Get cyclists by country",
            tags = {"Cyclist"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "country", description = "String", example = "Spain")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CyclistDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> getCyclistsByCountry(GetCyclistsByCountryUseCase useCase) {
        return route(GET("/cyclists/country/{country}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("country")), CyclistDTO.class))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "getCyclistsByTeamCode",
            summary = "Get cyclists by team code",
            tags = {"Cyclist"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "teamCode", description = "String", example = "MOV")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CyclistDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> getCyclistsByTeamCode(GetCyclistsByTeamUseCase useCase) {
        return route(GET("/teams/{teamCode}/cyclists").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("teamCode")), CyclistDTO.class))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "deleteCyclistById",
            summary = "Delete cyclist by id",
            tags = {"Cyclist"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "String")},
            responses = {
                    @ApiResponse(responseCode = "202", description = "Deleted"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> deleteCyclistById(DeleteCyclistByIdUseCase useCase) {
        return route(DELETE("/cyclists/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")), void.class))
        );
    }

}
