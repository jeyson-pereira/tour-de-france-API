package com.sofka.tourdefrance.usecases.cyclistusecase;

import com.sofka.tourdefrance.repositories.CyclistRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteCyclistByIdUseCase implements Function<String, Mono<Void>> {

    private final CyclistRepository cyclistRepository;

    public DeleteCyclistByIdUseCase(CyclistRepository cyclistRepository) {
        this.cyclistRepository = cyclistRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Cyclist id is required");
        return cyclistRepository.deleteById(id);
    }
}
