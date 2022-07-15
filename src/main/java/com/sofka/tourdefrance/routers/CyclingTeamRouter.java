package com.sofka.tourdefrance.routers;

import com.sofka.tourdefrance.models.CyclingTeamDTO;
import com.sofka.tourdefrance.usecases.teamusecase.*;
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
public class CyclingTeamRouter {

    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "getAllCyclingTeams",
            summary = "Get all cycling teams",
            tags = {"CyclingTeam"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CyclingTeamDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> getAllCyclingTeams(GetTeamsUseCase getTeamsUseCase) {
        return route(GET("/teams").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getTeamsUseCase.get(), CyclingTeamDTO.class))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "createCyclingTeam",
            summary = "Create a cycling team",
            tags = {"CyclingTeam"},
            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = CyclingTeamDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CyclingTeamDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }))
    public RouterFunction<ServerResponse> createCyclingTeam(CreateTeamUseCase createTeamUseCase) {
        Function<CyclingTeamDTO, Mono<ServerResponse>> executor = cyclingTeamDTO -> createTeamUseCase.apply(cyclingTeamDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result))
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(String.format(
                                "Error: A team already exists with this code (%s)", cyclingTeamDTO.getTeamCode()
                        )));
        return route(
                POST("/teams").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclingTeamDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue("Error: " + throwable.getMessage()))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "updateCyclingTeamById",
            summary = "Update a cycling team by id",
            tags = {"CyclingTeam"},
            parameters = @Parameter(name = "id", in = ParameterIn.PATH, description = "Id of the team to update", required = true),
            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = CyclingTeamDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CyclingTeamDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }))
    public RouterFunction<ServerResponse> updateCyclingTeamById(UpdateCyclingTeamUseCase useCase) {
        Function<String, Function<CyclingTeamDTO, Mono<ServerResponse>>> executor = id -> cyclingTeamDTO -> useCase.apply(id, cyclingTeamDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result))
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(e.getMessage()));
        return route(
                PUT("/teams/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CyclingTeamDTO.class).flatMap(executor.apply(request.pathVariable("id")))
                        .onErrorResume(throwable -> ServerResponse.badRequest()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue("Error: " + throwable.getMessage()))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "getCyclingTeamsByCountry",
            summary = "Get cycling teams by country",
            tags = {"CyclingTeam"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "country", description = "String", example = "Spain")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CyclingTeamDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> getCyclingTeamsByCountry(GetTeamsByCountryUseCase useCase) {
        return route(GET("/teams/country/{country}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("country")), CyclingTeamDTO.class))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "getCyclingTeamByTeamCode",
            summary = "Get cycling team by team code",
            tags = {"CyclingTeam"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "teamCode", description = "String", example = "MOV")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CyclingTeamDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> getCyclingTeamByTeamCode(GetByTeamCodeUseCase useCase) {
        return route(GET("/teams/{teamCode}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("teamCode")), CyclingTeamDTO.class))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(
            operationId = "deleteCyclingTeamById",
            summary = "Delete cycling team by id",
            tags = {"CyclingTeam"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "String")},
            responses = {
                    @ApiResponse(responseCode = "202", description = "Deleted"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }))
    public RouterFunction<ServerResponse> deleteCyclingTeamById(DeleteTeamByIdUseCase useCase) {
        return route(DELETE("/teams/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")), void.class))
        );
    }

}
