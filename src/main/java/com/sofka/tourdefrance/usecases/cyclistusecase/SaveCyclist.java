package com.sofka.tourdefrance.usecases.cyclistusecase;

import com.sofka.tourdefrance.models.CyclistDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface SaveCyclist {
    Mono<CyclistDTO> apply(@Valid CyclistDTO cyclistDTO);
}
