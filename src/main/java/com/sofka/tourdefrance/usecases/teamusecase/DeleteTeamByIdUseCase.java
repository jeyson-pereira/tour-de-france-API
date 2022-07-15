package com.sofka.tourdefrance.usecases.teamusecase;

import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import com.sofka.tourdefrance.repositories.CyclistRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteTeamByIdUseCase implements Function<String, Mono<Void>> {
    private final CyclingTeamRepository teamRepository;
    private final CyclistRepository cyclistRepository;

    public DeleteTeamByIdUseCase(CyclingTeamRepository teamRepository, CyclistRepository cyclistRepository) {
        this.teamRepository = teamRepository;
        this.cyclistRepository = cyclistRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Team id is required");
        return teamRepository.deleteById(id)
                .switchIfEmpty(Mono.defer(() -> cyclistRepository.deleteByCyclingTeamId(id)));
    }
}
