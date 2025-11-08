package org.deblock.exercise.adapter.outbound.provider.toughjet

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "tough-jet")
data class ToughJetProperties(

  @field:NotBlank
  val baseUrl: String
)
