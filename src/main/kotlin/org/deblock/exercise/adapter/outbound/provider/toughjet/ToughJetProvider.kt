package org.deblock.exercise.adapter.outbound.provider.toughjet

import org.deblock.exercise.adapter.outbound.provider.toughjet.dto.ToughJetFlight
import org.deblock.exercise.adapter.outbound.provider.toughjet.dto.ToughJetSearchFlightsRequest
import org.deblock.exercise.domain.model.Flight
import org.deblock.exercise.domain.model.FlightSearchOptions
import org.deblock.exercise.domain.ports.out.FlightProvider
import reactor.core.publisher.Mono
import java.math.BigDecimal

class ToughJetProvider(private val client: ToughJetHttpClient) : FlightProvider {

  override fun search(options: FlightSearchOptions): Mono<List<Flight>> {
    return client.search(options.toToughJetRequest())
      .map { list ->
        list.map { it.toFlight() }
      }
  }
}

fun FlightSearchOptions.toToughJetRequest() = ToughJetSearchFlightsRequest(
  from = origin,
  to = destination,
  outboundDate = departureDate,
  inboundDate = returnDate,
  numberOfAdults = numberOfPassengers,
)

fun ToughJetFlight.toFlight(): Flight {
  val fare = ((basePrice + tax) * (BigDecimal.ONE - discount / BigDecimal(100)))

  return Flight(
    airline = carrier,
    supplier = "ToughJet",
    fare = fare,
    departureAirportCode = departureAirportName,
    destinationAirportCode = arrivalAirportName,
    departureDate = outboundDateTime,
    arrivalDate = inboundDateTime
  )
}
