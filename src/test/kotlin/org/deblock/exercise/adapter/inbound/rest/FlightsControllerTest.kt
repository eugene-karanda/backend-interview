package org.deblock.exercise.adapter.inbound.rest

import org.deblock.exercise.adapter.inbound.rest.dto.FlightResponse
import org.deblock.exercise.adapter.inbound.rest.dto.SearchFlightsRequest
import org.deblock.exercise.domain.model.Flight
import org.deblock.exercise.domain.ports.`in`.SearchFlightsUseCase
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@WebFluxTest(FlightsController::class)
class FlightsControllerTest(
  @param:Autowired val webTestClient: WebTestClient
) {

  @MockBean
  lateinit var searchFlightsUseCase: SearchFlightsUseCase

  @Test
  fun `should return flights`() {
    // given
    val flight = Flight(
      airline = "CrazyAir",
      supplier = "CrazyAir",
      fare = BigDecimal("123.45"),
      departureAirportCode = "LHR",
      destinationAirportCode = "CDG",
      departureDate = LocalDateTime.of(2025, 12, 1, 10, 0),
      arrivalDate = LocalDateTime.of(2025, 12, 1, 12, 0)
    )

    val request = SearchFlightsRequest(
      origin = "LHR",
      destination = "CDG",
      departureDate = LocalDate.of(2025, 12, 1),
      returnDate = LocalDate.of(2025, 12, 5),
      numberOfPassengers = 2
    )

    given(searchFlightsUseCase.search(request.toFlightSearchOptions()))
      .willReturn(Mono.just(listOf(flight)))

    // when / then
    webTestClient.post()
      .uri("/v1/flights/search")
      .bodyValue(request)
      .exchange()
      .expectStatus().isOk
      .expectBodyList(FlightResponse::class.java)
      .hasSize(1)
      .contains(
        FlightResponse(
          airline = flight.airline,
          supplier = flight.supplier,
          fare = flight.fare.toPlainString(),
          departureAirportCode = flight.departureAirportCode,
          destinationAirportCode = flight.destinationAirportCode,
          departureDate = flight.departureDate,
          arrivalDate = flight.arrivalDate
        )
      )
  }

  @Test
  fun `should reject invalid origin IATA code`() {
    // given
    val today = LocalDate.now()
    val body = SearchFlightsRequest(
      origin = "LoN",
      destination = "CDG",
      departureDate = today,
      returnDate = today.plusDays(2),
      numberOfPassengers = 1
    )

    // when / then
    webTestClient.post()
      .uri("/v1/flights/search")
      .contentType(APPLICATION_JSON)
      .bodyValue(body)
      .exchange()
      .expectStatus().isBadRequest

    then(searchFlightsUseCase).shouldHaveNoInteractions()
  }
}
