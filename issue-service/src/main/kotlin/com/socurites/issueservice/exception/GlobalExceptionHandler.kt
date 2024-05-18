package com.socurites.issueservice.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger {  }

    @ExceptionHandler(ServerException::class)
    fun handleServerException(exception: ServerException): ErrorResponse {
        logger.error { exception.message }

        return ErrorResponse(code = exception.code, message = exception.message)
    }

    @ExceptionHandler(TokenExpiredException::class)
    fun handleTokenExpiredException(exception: TokenExpiredException): ErrorResponse {
        logger.error { exception.message }

        return ErrorResponse(code = HttpStatus.UNAUTHORIZED.value(), message = "Token expired")
    }

    @ExceptionHandler(Exception::class)
    fun handleServerException(exception: Exception): ErrorResponse {
        logger.error { exception.message }

        return ErrorResponse(code = HttpStatus.INTERNAL_SERVER_ERROR.value(), message = "Internal Server Error")
    }
}