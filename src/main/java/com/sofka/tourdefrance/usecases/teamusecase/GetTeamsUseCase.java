package com.sofka.tourdefrance.usecases.teamusecase;

import com.sofka.tourdefrance.models.CyclingTeamDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclingTeam;
import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetTeamsUseCase implements Supplier<Flux<CyclingTeamDTO>> {
    private final CyclingTeamRepository teamRepository;
    private final MapperCyclingTeam teamMapper;

    public GetTeamsUseCase(CyclingTeamRepository teamRepository, MapperCyclingTeam teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public Flux<CyclingTeamDTO> get() {
        return teamRepository.findAll()
                .map(teamMapper.toDTO);
    }
}
