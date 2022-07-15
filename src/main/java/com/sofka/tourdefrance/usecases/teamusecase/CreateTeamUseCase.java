package com.sofka.tourdefrance.usecases.teamusecase;

import com.sofka.tourdefrance.models.CyclingTeamDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclingTeam;
import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateTeamUseCase implements SaveCyclingTeam {
    private final CyclingTeamRepository teamRepository;
    private final MapperCyclingTeam teamMapper;

    public CreateTeamUseCase(CyclingTeamRepository teamRepository, MapperCyclingTeam teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public Mono<CyclingTeamDTO> apply(CyclingTeamDTO cyclingTeamDTO) {
        return teamRepository.save(teamMapper.toEntity.apply(cyclingTeamDTO))
                .map(teamMapper.toDTO);
    }

}
