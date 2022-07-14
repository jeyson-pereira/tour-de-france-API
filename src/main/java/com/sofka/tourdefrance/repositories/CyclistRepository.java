package com.sofka.tourdefrance.repositories;

import com.sofka.tourdefrance.collections.Cyclist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CyclistRepository extends ReactiveCrudRepository<Cyclist, String> {
    Flux<Cyclist> findCyclistsByCyclingTeamCode(String cyclingTeamCode);
    Flux<Cyclist> findCyclistsByCountry(String country);
}
