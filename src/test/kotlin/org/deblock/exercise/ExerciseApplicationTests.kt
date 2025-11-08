package org.deblock.exercise

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest
class ExerciseApplicationTests {

  companion object {

    @JvmStatic
    @Suppress("unused")
    @DynamicPropertySource
    fun registerProps(registry: DynamicPropertyRegistry) {
      registry.add("crazy-air.base-url") { "https://crazy-air.com/api" }
      registry.add("tough-jet.base-url") { "https://api.tough-jet.com/v1" }
    }
  }

  @Test
  fun `should load context`() {

  }
}
