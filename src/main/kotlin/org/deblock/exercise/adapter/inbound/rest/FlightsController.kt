package org.deblock.exercise.adapter.inbound.rest

import jakarta.validation.Valid
import org.deblock.exercise.adapter.inbound.rest.dto.FlightResponse
import org.deblock.exercise.adapter.inbound.rest.dto.SearchFlightsRequest
import org.deblock.exercise.domain.model.Flight
import org.deblock.exercise.domain.model.FlightSearchOptions
import org.deblock.exercise.domain.ports.`in`.SearchFlightsUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.text.DecimalFormat

@Validated
@RestController
@RequestMapping("v1/flights")
class FlightsController(private val searchFlightsUseCase: SearchFlightsUseCase) {

  @PostMapping("search")
  fun search(@Valid @RequestBody request: SearchFlightsRequest): Mono<List<FlightResponse>> =
    searchFlightsUseCase.search(request.toFlightSearchOptions())
      .map { list ->
        list.map { it.toFlightResponse()}
      }
}

fun SearchFlightsRequest.toFlightSearchOptions() = FlightSearchOptions(
  origin = origin,
  destination = destination,
  departureDate = departureDate,
  returnDate = returnDate,
  numberOfPassengers = numberOfPassengers
)

val fareDecimalFormat = DecimalFormat("#0.##").also {
  it.minimumFractionDigits = 2
}

fun Flight.toFlightResponse() = FlightResponse(
  airline = airline,
  supplier = supplier,
  fare = fareDecimalFormat.format(fare),
  departureAirportCode = departureAirportCode,
  destinationAirportCode = destinationAirportCode,
  departureDate = departureDate,
  arrivalDate = arrivalDate
)
