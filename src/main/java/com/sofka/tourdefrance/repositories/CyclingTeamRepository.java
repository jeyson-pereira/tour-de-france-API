package com.sofka.tourdefrance.repositories;

import com.sofka.tourdefrance.collections.CyclingTeam;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CyclingTeamRepository extends ReactiveCrudRepository<CyclingTeam, String> {
    Flux<CyclingTeam> findTeamsByCountry(String country);
    Mono<CyclingTeam> findTeamByTeamCode(String teamCode);
}
