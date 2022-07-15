package com.sofka.tourdefrance.usecases.cyclistusecase;

import com.sofka.tourdefrance.models.CyclistDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclist;
import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import com.sofka.tourdefrance.repositories.CyclistRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class GetCyclistsByTeamUseCase implements Function<String, Flux<CyclistDTO>> {
    private final CyclistRepository cyclistRepository;
    private final CyclingTeamRepository teamRepository;
    private final MapperCyclist cyclistMapper;

    public GetCyclistsByTeamUseCase(CyclistRepository cyclistRepository, CyclingTeamRepository teamRepository, MapperCyclist cyclistMapper) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
        this.cyclistMapper = cyclistMapper;
    }

    @Override
    public Flux<CyclistDTO> apply(String teamCode) {
        return cyclistRepository.findCyclistsByCyclingTeamCode(teamCode.toUpperCase(), Sort.by("competitorNumber"))
                .map(cyclistMapper.toDTO);
    }
}
