package br.com.fiap.mikes.adapter.inbound.controller.exceptionhandler

import br.com.fiap.mikes.application.core.domain.exception.InvalidValueException
import br.com.fiap.mikes.application.core.usecase.exception.AlreadyExistsException
import br.com.fiap.mikes.application.core.usecase.exception.InvalidDomainStateException
import br.com.fiap.mikes.application.core.usecase.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        const val TYPE_INVALID_VALUE_EXCEPTION = "INVALID_DATA"
        const val TYPE_NOT_FOUND_EXCEPTION = "NOT_FOUND"
        const val TYPE_ALREADY_EXISTS_EXCEPTION = "ALREADY_EXISTS"
        const val TYPE_INVALID_DOMAIN_STATE_EXCEPTION = "INVALID_DOMAIN_STATE"
    }

    @ExceptionHandler(InvalidValueException::class)
    fun invalidValueExceptionHandler(e: InvalidValueException, request: WebRequest): ResponseEntity<Error> {
        val message = "Invalid value for '${e.type}': ${e.message}"

        return responseFactory(
            HttpStatus.BAD_REQUEST,
            TYPE_INVALID_VALUE_EXCEPTION,
            message,
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundExceptionHandler(e: NotFoundException, request: WebRequest): ResponseEntity<Error> {
        val message = "'${e.type}' not found: '${e.message}'"

        return responseFactory(
            HttpStatus.NOT_FOUND,
            TYPE_NOT_FOUND_EXCEPTION,
            message,
        )
    }

    @ExceptionHandler(AlreadyExistsException::class)
    fun alreadyExistsExceptionHandler(e: AlreadyExistsException, request: WebRequest): ResponseEntity<Error> {
        val message = "'${e.type}' already exists: '${e.message}'"

        return responseFactory(
            HttpStatus.CONFLICT,
            TYPE_ALREADY_EXISTS_EXCEPTION,
            message,
        )
    }

    @ExceptionHandler(InvalidDomainStateException::class)
    fun invalidDomainStateExceptionHandler(e: InvalidDomainStateException, request: WebRequest): ResponseEntity<Error> {
        val message = "found '${e.type}' in invalid state: '${e.message}'"

        return responseFactory(
            HttpStatus.INTERNAL_SERVER_ERROR,
            TYPE_INVALID_DOMAIN_STATE_EXCEPTION,
            message,
        )
    }

    private fun responseFactory(httpStatusCode: HttpStatusCode, error: String, message: String): ResponseEntity<Error> {
        return ResponseEntity
            .status(httpStatusCode)
            .body(Error(error, message))
    }

    class Error(
        val error: String,
        val message: String,
    )
}
