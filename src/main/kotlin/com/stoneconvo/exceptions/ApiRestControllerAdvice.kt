package com.stoneconvo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@RestControllerAdvice
class ApiRestControllerAdvice {
    data class ErrorResponseBody(
        val message: String?,
        val status: Int,
        val timestamp: LocalDateTime
    )

    @ExceptionHandler
    fun handleUserAccountNotFoundException(
        ex: UserAccountNotFoundException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = ex.message,
            status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.UNPROCESSABLE_ENTITY
    )

    @ExceptionHandler
    fun handleResponseStatusException(
        ex: ResponseStatusException
    ) = ResponseEntity("OK", HttpStatus.OK)
}
