package org.deblock.exercise.adapter.inbound.rest.dto

data class ValidationError(
    val field: String?,
    val message: String,
    val code: String?,
    val rejectedValue: Any? = null
)
