package com.sofka.tourdefrance.repositories;

import com.sofka.tourdefrance.collections.Cyclist;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CyclistRepository extends ReactiveCrudRepository<Cyclist, String> {
    Flux<Cyclist> findCyclistsByCyclingTeamCode(String cyclingTeamCode, Sort sort);
    Flux<Cyclist> findCyclistsByCountry(String country, Sort sort);
    Mono<Void> deleteByCyclingTeamId(String cyclingTeamId);
    Flux<Cyclist> findAll(Sort sort);

    Flux<Cyclist> findAllByCyclingTeamId(String cyclingTeamId);
}
