package com.sofka.tourdefrance.usecases.teamusecase;

import com.sofka.tourdefrance.models.CyclingTeamDTO;
import com.sofka.tourdefrance.models.mapper.MapperCyclingTeam;
import com.sofka.tourdefrance.repositories.CyclingTeamRepository;
import com.sofka.tourdefrance.repositories.CyclistRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class UpdateCyclingTeamUseCase implements UpdateCyclingTeam {
    private final CyclingTeamRepository teamRepository;
    private final CyclistRepository cyclistRepository;
    private final MapperCyclingTeam teamMapper;

    public UpdateCyclingTeamUseCase(CyclingTeamRepository teamRepository, CyclistRepository cyclistRepository, MapperCyclingTeam teamMapper) {
        this.teamRepository = teamRepository;
        this.cyclistRepository = cyclistRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public Mono<CyclingTeamDTO> apply(String id, CyclingTeamDTO updatedTeamDTO) {
        var updatedTeam = teamMapper.toEntity.apply(updatedTeamDTO);
        return teamRepository.findById(id)
                .flatMap(team -> {
                    team.setName(updatedTeam.getName());
                    team.setTeamCode(updatedTeam.getTeamCode());
                    team.setCountry(updatedTeam.getCountry());

                    //Update cyclists team code to new team code and save both
                    return cyclistRepository.findAllByCyclingTeamId(id)
                            .flatMap(cyclist -> {
                                cyclist.setCyclingTeamCode(updatedTeam.getTeamCode());
                                return cyclistRepository.save(cyclist);
                            })
                            .then(teamRepository.save(team))
                            .then(Mono.just(team));

                })
                .map(teamMapper.toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Error: Team not found with id " + id)));
    }
}
