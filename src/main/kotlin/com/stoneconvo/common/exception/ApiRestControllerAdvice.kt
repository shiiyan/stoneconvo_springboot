package com.stoneconvo.common.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime

@RestControllerAdvice
class ApiRestControllerAdvice {
    private val logger = LoggerFactory.getLogger(ApiRestControllerAdvice::class.java)

    data class ErrorResponseBody(
        val message: String?,
        val status: Int,
        val timestamp: LocalDateTime
    )

    @ExceptionHandler
    fun handleUnauthorizedException(
        ex: CustomException.UnauthorizedException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = ex.message,
            status = HttpStatus.UNAUTHORIZED.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.UNAUTHORIZED
    )

    @ExceptionHandler
    fun handleEntityNotFoundException(
        ex: CustomException.EntityNotFoundException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = ex.message,
            status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.UNPROCESSABLE_ENTITY
    )

    @ExceptionHandler
    fun handleEntityAlreadyExistException(
        ex: CustomException.EntityAlreadyExistException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = ex.message,
            status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.UNPROCESSABLE_ENTITY
    )

    @ExceptionHandler
    fun handleEntityIllegalStateException(
        ex: CustomException.EntityIllegalStateException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = ex.message,
            status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.UNPROCESSABLE_ENTITY
    )

    @ExceptionHandler
    fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = ex.message,
            status = HttpStatus.NOT_FOUND.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.NOT_FOUND
    )

    @ExceptionHandler
    fun handleHttpRequestMethodNotSupportedException(
        ex: HttpRequestMethodNotSupportedException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = ex.message,
            status = HttpStatus.METHOD_NOT_ALLOWED.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.METHOD_NOT_ALLOWED
    )

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponseBody> = ResponseEntity<ErrorResponseBody>(
        ErrorResponseBody(
            message = "Method Argument Not Valid",
            status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            timestamp = LocalDateTime.now()
        ),
        HttpStatus.UNPROCESSABLE_ENTITY
    )

    @ExceptionHandler
    fun handleGeneralException(
        ex: Exception
    ): ResponseEntity<ErrorResponseBody> {
        val customMessage = "Internal Server Error - ${ex.message}"
        logger.error(customMessage, ex)

        return ResponseEntity<ErrorResponseBody>(
            ErrorResponseBody(
                message = "Internal Server Error",
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                timestamp = LocalDateTime.now()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
