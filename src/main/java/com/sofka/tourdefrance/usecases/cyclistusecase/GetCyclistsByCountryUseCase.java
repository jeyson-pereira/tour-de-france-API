package com.sofka.tourdefrance.usecases.cyclistusecase;

import com.sofka.tourdefrance.models.CyclistDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclist;
import com.sofka.tourdefrance.repositories.CyclistRepository;
import org.apache.commons.text.WordUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class GetCyclistsByCountryUseCase implements Function<String, Flux<CyclistDTO>> {
    private final CyclistRepository cyclistRepository;
    private final MapperCyclist cyclistMapper;

    public GetCyclistsByCountryUseCase(CyclistRepository cyclistRepository, MapperCyclist cyclistMapper) {
        this.cyclistRepository = cyclistRepository;
        this.cyclistMapper = cyclistMapper;
    }

    @Override
    public Flux<CyclistDTO> apply(String country) {
        return cyclistRepository.findCyclistsByCountry(
                WordUtils.capitalizeFully(country), Sort.by("fullName"))
                .map(cyclistMapper.toDTO);
    }
}
