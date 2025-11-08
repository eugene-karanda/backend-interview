package org.deblock.exercise.adapter.outbound.provider.toughjet.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ToughJetFlight(
  val carrier: String,
  val basePrice: BigDecimal,
  val tax: BigDecimal,
  val discount: BigDecimal,
  val departureAirportName: String,
  val arrivalAirportName: String,
  val outboundDateTime: LocalDateTime,
  val inboundDateTime: LocalDateTime
)
