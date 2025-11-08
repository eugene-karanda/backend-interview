package org.deblock.exercise.adapter.outbound.provider.crazyair

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "crazy-air")
data class CrazyAirProperties(
  @field:NotBlank
  val baseUrl: String
)
