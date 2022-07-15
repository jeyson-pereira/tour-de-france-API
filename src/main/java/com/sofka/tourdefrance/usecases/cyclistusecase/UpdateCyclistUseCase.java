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
public class UpdateCyclistUseCase implements UpdateCyclist{

    private final CyclistRepository cyclistRepository;
    private final CyclingTeamRepository teamRepository;
    private final MapperCyclist cyclistMapper;

    public UpdateCyclistUseCase(CyclistRepository cyclistRepository, CyclingTeamRepository teamRepository, MapperCyclist cyclistMapper) {
        this.cyclistRepository = cyclistRepository;
        this.teamRepository = teamRepository;
        this.cyclistMapper = cyclistMapper;
    }


    @Override
    public Mono<CyclistDTO> apply(String id, CyclistDTO updatedCyclistDTO) {
        var updatedCyclist = cyclistMapper.toEntity.apply(updatedCyclistDTO);
        return teamRepository.findTeamByTeamCode(updatedCyclist.getCyclingTeamCode()).flatMap(team -> {
                    updatedCyclist.setCyclingTeamId(team.getId());
                    return cyclistRepository.findById(id)
                            .flatMap(cyclist -> {
                                cyclist.setFullName(updatedCyclist.getFullName());
                                cyclist.setCompetitorNumber(updatedCyclist.getCompetitorNumber());
                                cyclist.setCountry(updatedCyclist.getCountry());
                                cyclist.setCyclingTeamId(updatedCyclist.getCyclingTeamId());
                                cyclist.setCyclingTeamCode(updatedCyclist.getCyclingTeamCode());
                                return cyclistRepository.save(cyclist);
                            }).switchIfEmpty(Mono.error(new IllegalArgumentException("Error: Cyclist not found with id " + id)));
                }).map(cyclistMapper.toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Error: Team not found with code " + updatedCyclist.getCyclingTeamCode())));
    }
}
