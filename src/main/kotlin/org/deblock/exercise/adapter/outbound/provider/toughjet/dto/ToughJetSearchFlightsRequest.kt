package org.deblock.exercise.adapter.outbound.provider.toughjet.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class ToughJetSearchFlightsRequest(
  val from: String,

  val to: String,

  @field:JsonFormat("yyyy-MM-dd")
  val outboundDate: LocalDate,

  @field:JsonFormat("yyyy-MM-dd")
  val inboundDate: LocalDate,

  val numberOfAdults: Int
)
