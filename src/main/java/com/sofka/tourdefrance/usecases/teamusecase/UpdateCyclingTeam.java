package com.sofka.tourdefrance.usecases.teamusecase;

import com.sofka.tourdefrance.models.CyclingTeamDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface UpdateCyclingTeam {
    Mono<CyclingTeamDTO> apply(String id, @Valid CyclingTeamDTO cyclingTeamDTO);
}
