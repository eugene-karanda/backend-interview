package org.deblock.exercise.adapter.inbound.rest.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ReturnAfterDepartureValidator::class])
annotation class ReturnAfterDeparture(
  val message: String = "returnDate must be after departureDate",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []
)
