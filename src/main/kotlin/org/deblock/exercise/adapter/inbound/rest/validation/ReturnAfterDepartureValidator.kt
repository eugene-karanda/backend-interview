package org.deblock.exercise.adapter.inbound.rest.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.deblock.exercise.adapter.inbound.rest.dto.SearchFlightsRequest

class ReturnAfterDepartureValidator : ConstraintValidator<ReturnAfterDeparture, SearchFlightsRequest> {

  override fun isValid(value: SearchFlightsRequest?, context: ConstraintValidatorContext): Boolean {
    if (value == null) return true
    return value.returnDate.isAfter(value.departureDate)
  }
}
