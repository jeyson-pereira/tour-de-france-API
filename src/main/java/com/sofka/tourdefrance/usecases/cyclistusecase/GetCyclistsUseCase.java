package com.sofka.tourdefrance.usecases.cyclistusecase;

import com.sofka.tourdefrance.models.CyclistDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclist;
import com.sofka.tourdefrance.repositories.CyclistRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetCyclistsUseCase implements Supplier<Flux<CyclistDTO>> {

    private final CyclistRepository cyclistRepository;
    private final MapperCyclist cyclistMapper;

    public GetCyclistsUseCase(CyclistRepository cyclistRepository, MapperCyclist cyclistMapper) {
        this.cyclistRepository = cyclistRepository;
        this.cyclistMapper = cyclistMapper;
    }

    @Override
    public Flux<CyclistDTO> get() {
        return cyclistRepository.findAll(Sort.by("competitorNumber"))
                .map(cyclistMapper.toDTO);
    }

}
