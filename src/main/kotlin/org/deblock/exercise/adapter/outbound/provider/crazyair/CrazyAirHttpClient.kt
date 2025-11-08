package org.deblock.exercise.adapter.outbound.provider.crazyair

import org.deblock.exercise.adapter.outbound.provider.crazyair.dto.CrazyAirFlight
import org.deblock.exercise.adapter.outbound.provider.crazyair.dto.CrazyAirSearchFlightsRequest
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Mono

interface CrazyAirHttpClient {

  @PostExchange(url = "flights")
  fun search(@RequestBody request: CrazyAirSearchFlightsRequest) : Mono<List<CrazyAirFlight>>
}
