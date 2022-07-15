package com.sofka.tourdefrance.usecases.cyclistusecase;

import com.sofka.tourdefrance.models.CyclistDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface UpdateCyclist {
    Mono<CyclistDTO> apply(String id, @Valid CyclistDTO cyclistDTO);
}
