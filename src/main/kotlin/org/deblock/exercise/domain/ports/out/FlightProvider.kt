package org.deblock.exercise.domain.ports.out

import org.deblock.exercise.domain.model.Flight
import org.deblock.exercise.domain.model.FlightSearchOptions
import reactor.core.publisher.Mono

interface FlightProvider {

  fun search(options: FlightSearchOptions) : Mono<List<Flight>>
}
