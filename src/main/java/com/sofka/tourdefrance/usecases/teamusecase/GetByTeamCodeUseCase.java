package com.sofka.tourdefrance.usecases.teamusecase;


import com.sofka.tourdefrance.models.CyclingTeamDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclingTeam;
import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class GetByTeamCodeUseCase implements Function<String , Mono<CyclingTeamDTO>> {
    private final CyclingTeamRepository teamRepository;
    private final MapperCyclingTeam teamMapper;

    public GetByTeamCodeUseCase(CyclingTeamRepository teamRepository, MapperCyclingTeam teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public Mono<CyclingTeamDTO> apply(String teamCode) {
        return teamRepository.findTeamByTeamCode(teamCode.toUpperCase())
                .map(teamMapper.toDTO);
    }
}
