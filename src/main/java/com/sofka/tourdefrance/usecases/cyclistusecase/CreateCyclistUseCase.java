package com.sofka.tourdefrance.usecases.cyclistusecase;

import com.sofka.tourdefrance.models.CyclistDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclist;
import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import com.sofka.tourdefrance.repositories.CyclistRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateCyclistUseCase implements SaveCyclist {
    private final CyclistRepository cyclistRepository;
    private final CyclingTeamRepository teamRepository;
    private final MapperCyclist cyclistMapper;

    public CreateCyclistUseCase(CyclistRepository cyclistRepository, CyclingTeamRepository teamRepository , MapperCyclist cyclistMapper) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
        this.cyclistMapper = cyclistMapper;
    }

    @Override
    public Mono<CyclistDTO> apply(CyclistDTO cyclistDTO) {
        return teamRepository.findTeamByTeamCode(cyclistDTO.getCyclingTeamCode()).flatMap(team -> {
            cyclistDTO.setCyclingTeamId(team.getId());
            return cyclistRepository.save(cyclistMapper.toEntity.apply(cyclistDTO));
        }).map(cyclistMapper.toDTO)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Error: Team not found with code " + cyclistDTO.getCyclingTeamCode())));
    }
}
