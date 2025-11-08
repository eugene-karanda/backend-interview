package org.deblock.exercise.adapter.inbound.rest.dto

import java.time.LocalDateTime

data class FlightResponse(
  val airline: String,
  val supplier: String,
  val fare: String,
  val departureAirportCode: String,
  val destinationAirportCode: String,
  val departureDate: LocalDateTime,
  val arrivalDate: LocalDateTime
)
