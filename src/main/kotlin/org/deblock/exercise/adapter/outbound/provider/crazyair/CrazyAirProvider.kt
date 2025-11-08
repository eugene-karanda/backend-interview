package org.deblock.exercise.adapter.outbound.provider.crazyair

import org.deblock.exercise.adapter.outbound.provider.crazyair.dto.CrazyAirFlight
import org.deblock.exercise.adapter.outbound.provider.crazyair.dto.CrazyAirSearchFlightsRequest
import org.deblock.exercise.domain.model.Flight
import org.deblock.exercise.domain.model.FlightSearchOptions
import org.deblock.exercise.domain.ports.out.FlightProvider
import reactor.core.publisher.Mono

class CrazyAirProvider(private val client: CrazyAirHttpClient) : FlightProvider {

  override fun search(options: FlightSearchOptions): Mono<List<Flight>> {
    return client.search(options.toCrazyAirRequest())
      .map { list ->
        list.map { it.toFlight() }
      }
  }
}

fun FlightSearchOptions.toCrazyAirRequest() = CrazyAirSearchFlightsRequest(
  origin = origin,
  destination = destination,
  departureDate = departureDate,
  returnDate = returnDate,
  passengerCount = numberOfPassengers,
)

fun CrazyAirFlight.toFlight() = Flight(
  airline = airline,
  supplier = "CrazyAir",
  fare = price,
  departureAirportCode = departureAirportCode,
  destinationAirportCode = destinationAirportCode,
  departureDate = departureDate,
  arrivalDate = arrivalDate
)
