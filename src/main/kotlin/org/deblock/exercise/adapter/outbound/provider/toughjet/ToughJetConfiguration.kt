package org.deblock.exercise.adapter.outbound.provider.toughjet

import org.deblock.exercise.domain.ports.out.FlightProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
@EnableConfigurationProperties(ToughJetProperties::class)
class ToughJetConfiguration(private val properties: ToughJetProperties) {

  @Bean
  fun toughJetProvider(httpClient: ToughJetHttpClient): FlightProvider {
    return ToughJetProvider(httpClient)
  }

  @Bean
  fun toughJetHttpClient(): ToughJetHttpClient {
    val webClient = WebClient.builder()
      .baseUrl(properties.baseUrl)
      .build()
    val adapter = WebClientAdapter.forClient(webClient)
    val factory = HttpServiceProxyFactory.builder()
      .clientAdapter(adapter)
      .build()

    return factory.createClient(ToughJetHttpClient::class.java)
  }
}
