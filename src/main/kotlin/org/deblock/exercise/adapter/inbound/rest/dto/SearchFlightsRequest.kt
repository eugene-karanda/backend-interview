package org.deblock.exercise.adapter.inbound.rest.dto

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.deblock.exercise.adapter.inbound.rest.validation.ReturnAfterDeparture
import java.time.LocalDate

@ReturnAfterDeparture
data class SearchFlightsRequest(

  @field:Pattern(regexp = "^[A-Z]{3}$")
  val origin: String,

  @field:Pattern(regexp = "^[A-Z]{3}$")
  val destination: String,

  @field:NotNull
  @field:FutureOrPresent
  val departureDate: LocalDate,

  @field:NotNull
  @field:FutureOrPresent
  val returnDate: LocalDate,

  @field:Min(1)
  @field:Max(4)
  val numberOfPassengers: Int
)
