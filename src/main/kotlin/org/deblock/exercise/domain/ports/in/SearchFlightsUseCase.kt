package org.deblock.exercise.domain.ports.`in`

import org.deblock.exercise.domain.model.Flight
import org.deblock.exercise.domain.model.FlightSearchOptions
import reactor.core.publisher.Mono

interface SearchFlightsUseCase {
  fun search(searchOptions: FlightSearchOptions): Mono<List<Flight>>
}
