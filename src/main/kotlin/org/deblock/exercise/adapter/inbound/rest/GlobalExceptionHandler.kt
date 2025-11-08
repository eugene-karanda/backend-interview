package org.deblock.exercise.adapter.inbound.rest

import org.deblock.exercise.adapter.inbound.rest.dto.ValidationError
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

@RestControllerAdvice
class GlobalExceptionHandler {

  private val logger = LoggerFactory.getLogger(this.javaClass)

  @ExceptionHandler(WebExchangeBindException::class)
  fun handleWebExchangeBind(ex: WebExchangeBindException): ProblemDetail {
    val fieldErrors = ex.bindingResult.fieldErrors.map {
      ValidationError(
        field = it.field,
        message = it.defaultMessage ?: it.code ?: "Invalid value",
        code = it.code,
        rejectedValue = it.rejectedValue
      )
    }
    val globalErrors = ex.bindingResult.globalErrors.map {
      ValidationError(
        field = null,
        message = it.defaultMessage ?: it.code ?: "Invalid object",
        code = it.code
      )
    }

    return ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Validation failed")
      .apply {
        title = "Bad Request"
        setProperty("errors", fieldErrors + globalErrors)
      }
  }

  @ResponseStatus(code = INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception::class)
  fun handleGenericException(ex: Exception): ProblemDetail {
    logger.error("Unhandled exception", ex)

    return ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, "Unexpected error")
      .apply {
        title = "Internal Server Error"
      }
  }
}
