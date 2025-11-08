package org.deblock.exercise.adapter.outbound.provider.toughjet

import org.deblock.exercise.adapter.outbound.provider.toughjet.dto.ToughJetFlight
import org.deblock.exercise.adapter.outbound.provider.toughjet.dto.ToughJetSearchFlightsRequest
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Mono

interface ToughJetHttpClient {

  @PostExchange(url = "jets")
  fun search(@RequestBody request: ToughJetSearchFlightsRequest) : Mono<List<ToughJetFlight>>
}
