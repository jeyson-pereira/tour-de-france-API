package com.sofka.tourdefrance.usecases.teamusecase;


import com.sofka.tourdefrance.models.CyclingTeamDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclingTeam;
import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class GetTeamsByCountryUseCase implements Function<String ,Flux<CyclingTeamDTO>> {
    private final CyclingTeamRepository teamRepository;
    private final MapperCyclingTeam teamMapper;

    public GetTeamsByCountryUseCase(CyclingTeamRepository teamRepository, MapperCyclingTeam teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public Flux<CyclingTeamDTO> apply(String country) {
        return teamRepository.findTeamsByCountry(WordUtils.capitalizeFully(country))
                .map(teamMapper.toDTO);
    }
}
