package com.example.pgsql

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class exceptionhandler {



    // 400 - Bad Request
    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: BadRequestException): Map<String, Any?> {
        return mapOf(
            "discription" to ex.message,
            "error_code" to HttpStatus.BAD_REQUEST.value()
        )
    }



    // 500 - Internal Server Error
    @ExceptionHandler(InternalServerErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerErrorException(ex: InternalServerErrorException): Map<String, Any?> {
        return mapOf(
            "discription" to ex.message,
            "error_code" to HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
    }

    // 409 - Conflict
    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleConflictException(ex: ConflictException): Map<String, Any?> {
        return mapOf(
            "description" to ex.message,
            "error_code" to HttpStatus.CONFLICT.value()
        )
    }
    // 404 - Resource Not Found
    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): Map<String, Any?> {
        return mapOf(
            "discription" to ex.message,
            "error_code" to HttpStatus.NOT_FOUND.value()
        )
    }
}