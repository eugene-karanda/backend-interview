package org.deblock.exercise.domain.service

import jakarta.validation.constraints.NotEmpty
import org.deblock.exercise.domain.model.Flight
import org.deblock.exercise.domain.model.FlightSearchOptions
import org.deblock.exercise.domain.ports.`in`.SearchFlightsUseCase
import org.deblock.exercise.domain.ports.out.FlightProvider
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class FlightService(@param:NotEmpty private val providers: List<FlightProvider>) : SearchFlightsUseCase {

  override fun search(searchOptions: FlightSearchOptions): Mono<List<Flight>> {
    return Flux.fromIterable(providers)
      .flatMap({ provider ->
        provider.search(searchOptions)
          .flatMapMany { Flux.fromIterable(it) }
      }, 2) // --> concurrency 2 <--
      .sort(Comparator.comparing { it.fare })
      .collectList()
  }
}
