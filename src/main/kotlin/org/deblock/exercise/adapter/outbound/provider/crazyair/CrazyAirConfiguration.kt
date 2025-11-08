package org.deblock.exercise.adapter.outbound.provider.crazyair

import org.deblock.exercise.domain.ports.out.FlightProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
@EnableConfigurationProperties(CrazyAirProperties::class)
class CrazyAirConfiguration(private val properties: CrazyAirProperties) {

  @Bean
  fun crazyAirProvider(httpClient: CrazyAirHttpClient): FlightProvider {
    return CrazyAirProvider(httpClient)
  }

  @Bean
  fun crazyAirHttpClient(): CrazyAirHttpClient {
    val webClient = WebClient.builder()
      .baseUrl(properties.baseUrl)
      .build()
    val adapter = WebClientAdapter.forClient(webClient)
    val factory = HttpServiceProxyFactory.builder()
      .clientAdapter(adapter)
      .build()

    return factory.createClient(CrazyAirHttpClient::class.java)
  }
}
