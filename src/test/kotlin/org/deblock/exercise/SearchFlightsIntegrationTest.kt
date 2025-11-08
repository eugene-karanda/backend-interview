package org.deblock.exercise

import org.deblock.exercise.adapter.inbound.rest.dto.FlightResponse
import org.deblock.exercise.adapter.inbound.rest.dto.SearchFlightsRequest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerExtension
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(MockServerExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchFlightsIntegrationTest(
  @param:Autowired val webTestClient: WebTestClient
) {

  companion object {

    lateinit var baseUrl: String

    @JvmStatic
    @BeforeAll
    fun init(mockServer: MockServerClient) {
      baseUrl = "http://localhost:${mockServer.port}"
    }

    @JvmStatic
    @Suppress("unused")
    @DynamicPropertySource
    fun registerProps(registry: DynamicPropertyRegistry) {
      registry.add("crazy-air.base-url") { "$baseUrl/crazy-air/" }
      registry.add("tough-jet.base-url") { "$baseUrl/tough-jet/" }
    }
  }

  @Test
  fun `should call endpoint and get flights`(mockServer: MockServerClient) {
    // given
    mockServer
      .`when`(
        request()
          .withMethod("POST")
          .withPath("/crazy-air/flights")
          .withBody(JsonBody.json(
            """
              {
                "origin": "LHR",
                "destination": "JFK",
                "departureDate": "2025-12-10",
                "returnDate": "2025-12-20",
                "passengerCount": 3
              }
            """.trimIndent())))
      .respond(
        response()
          .withStatusCode(200)
          .withBody(JsonBody.json(
            """
              [
                {
                  "airline": "Crazy Air Atlantic",
                  "price": 620.50,
                  "cabinclass": "E",
                  "departureAirportCode": "LHR",
                  "destinationAirportCode": "JFK",
                  "departureDate": "2025-12-10T11:00:00",
                  "arrivalDate": "2025-12-10T16:45:00"
                },
                {
                  "airline": "Crazy Air Atlantic",
                  "price": 950.99,
                  "cabinclass": "B",
                  "departureAirportCode": "LHR",
                  "destinationAirportCode": "JFK",
                  "departureDate": "2025-12-10T13:00:00",
                  "arrivalDate": "2025-12-10T18:55:00"
                }
              ]
            """.trimIndent()
          )))

    mockServer
      .`when`(
        request()
          .withMethod("POST")
          .withPath("/tough-jet/jets")
          .withBody(JsonBody.json(
            """
              {
                "from": "LHR",
                "to": "JFK",
                "outboundDate": "2025-12-10",
                "inboundDate": "2025-12-20",
                "numberOfAdults": 3
              }
            """.trimIndent())))
      .respond(
        response()
          .withStatusCode(200)
          .withBody(JsonBody.json(
            """
              [
                {
                  "carrier": "ToughJet Airways",
                  "basePrice": 100.00,
                  "tax": 20.00,
                  "discount": 10.00,
                  "departureAirportName": "LHR",
                  "arrivalAirportName": "JFK",
                  "outboundDateTime": "2025-12-11T16:13:00",
                  "inboundDateTime": "2025-12-11T20:18:00"
                }
              ]
            """.trimIndent()
          )))

    val request = SearchFlightsRequest(
      origin = "LHR",
      destination = "JFK",
      departureDate = LocalDate.of(2025, 12, 10),
      returnDate = LocalDate.of(2025, 12, 20),
      numberOfPassengers = 3
    )

    // when / then
    webTestClient.post()
      .uri("/v1/flights/search")
      .bodyValue(request)
      .exchange()
      .expectStatus().isOk
      .expectBodyList(FlightResponse::class.java)
      .hasSize(3)
      .contains(
        FlightResponse(
          airline = "ToughJet Airways",
          supplier = "ToughJet",
          fare = "108.00",
          departureAirportCode = "LHR",
          destinationAirportCode = "JFK",
          departureDate = LocalDateTime.of(2025, 12, 11, 16, 13),
          arrivalDate = LocalDateTime.of(2025, 12, 11, 20, 18)
        ),
        FlightResponse(
          airline = "Crazy Air Atlantic",
          supplier = "CrazyAir",
          fare = "620.50",
          departureAirportCode = "LHR",
          destinationAirportCode = "JFK",
          departureDate = LocalDateTime.of(2025, 12, 10, 11, 0),
          arrivalDate = LocalDateTime.of(2025, 12, 10, 16, 45)
        ),
        FlightResponse(
          airline = "Crazy Air Atlantic",
          supplier = "CrazyAir",
          fare = "950.99",
          departureAirportCode = "LHR",
          destinationAirportCode = "JFK",
          departureDate = LocalDateTime.of(2025, 12, 10, 13, 0),
          arrivalDate = LocalDateTime.of(2025, 12, 10, 18, 55)
        )
      )
  }
}
