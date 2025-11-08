package org.deblock.exercise.adapter.outbound.provider.crazyair.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class CrazyAirSearchFlightsRequest(
  val origin: String,

  val destination: String,

  @field:JsonFormat("yyyy-MM-dd")
  val departureDate: LocalDate,

  @field:JsonFormat("yyyy-MM-dd")
  val returnDate: LocalDate,

  val passengerCount: Int
)
