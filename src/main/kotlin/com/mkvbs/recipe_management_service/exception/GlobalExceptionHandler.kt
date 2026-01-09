package com.mkvbs.recipe_management_service.exception

import com.mkvbs.recipe_management_service.dto.ErrorResponseDto
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * A global exception handler class for intercepting and processing various exceptions
 * that occur during the execution of the application.
 *
 * This class is annotated with `@ControllerAdvice` to enable centralized exception
 * handling across all controllers. It provides specific handlers for a range of
 * exception types, including method argument validation errors, constraint violations,
 * and custom application-specific exceptions.
 *
 * Exception handlers in this class return standardized error responses encapsulated as
 * instances of `ResponseEntity` or `ErrorResponseDto`, which provide HTTP status codes
 * and descriptive error messages, aiding clients in understanding and processing errors.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler(
    private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
) : ResponseEntityExceptionHandler() {


    /**
     * Logs the details of an exception using the provided logger.
     * Use this method only for exceptions not related to [FymException]
     *
     * @param ex The exception to be logged, typically representing an error or
     * failure that needs to be recorded for debugging or auditing purposes.
     */
    private final fun logError(ex: Exception) {
        log.error(ex.message, ex)
    }

    /**
     * Handles generic exceptions that occur during application runtime.
     *
     * This global exception handler is used to catch all unhandled exceptions
     * and return an appropriate error response with a detailed message and
     * an HTTP status code of 500 (Internal Server Error).
     *
     * @param ex The exception to be handled. Typically represents a generic
     * exception where no specific handling logic is implemented.
     * @return A ResponseEntity containing an ErrorResponseDto, which includes
     * details about the error such as the error message, HTTP status code,
     * and a timestamp of when the exception occurred.
     */
    @ExceptionHandler(Exception::class)
    fun handleGlobalExceptions(ex: Exception): ResponseEntity<ErrorResponseDto> {
        val errorResponse = if (ex.message != null) {
            ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.message!!)
        } else {
            ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error, ${ex::class.simpleName}")
        }
        logError(ex)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * Handles exceptions related to method argument validation failures. This method processes
     * `MethodArgumentNotValidException` and extracts validation errors from the binding result,
     * returning them as a response entity with HTTP status `BAD_REQUEST`.
     *
     * @param ex The exception containing details about the validation errors for method arguments.
     * @param headers The HTTP headers passed along with the request.
     * @param status The HTTP status code for the response.
     * @param request The original request being processed.
     * @return A `ResponseEntity` containing a map of validation errors or null.
     */
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors: Map<String, String?> = ex.bindingResult.allErrors.associate { error ->
            val fieldName = if (error is FieldError) error.field else error.objectName
            fieldName to error.defaultMessage
        }

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    /**
     * Handles exceptions of type [ConstraintViolationException] and returns
     * a response with details of the constraint violations.
     *
     * This method processes the violations by extracting the field name and the
     * corresponding error message for each constraint violation, and returns this
     * information as a map in the response body with a HTTP 400 Bad Request status.
     *
     * @param ex The exception containing details about the violated constraints.
     *           Typically thrown when validation on method parameters fails.
     * @return A [ResponseEntity] containing a map where the keys represent
     *         the invalid field names and the values contain the associated
     *         error messages, along with an HTTP BAD_REQUEST status code.
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<Map<String, String?>> {
        val errors: Map<String, String?> = ex.constraintViolations.associate { violation ->
            val field = violation.propertyPath.toString().substringAfterLast(".")
            field to violation.message
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }


    // ----- FYM EXCEPTION HANDLERS -----


    /**
     * Handles exceptions of type [FymException] by creating and returning an appropriate
     * error response with an HTTP status code of 500 (Internal Server Error).
     *
     * This method processes [FymException] and creates an error response containing
     * details about the error, such as the HTTP status code, error message, and timestamp.
     *
     * @param ex The exception instance containing details about the application-specific error.
     * @return A [ResponseEntity] containing an [ErrorResponseDto] with error details.
     */
    @ExceptionHandler(FymException::class)
    fun handleFymException(ex: FymException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        logError(ex)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MissingDataException::class)
    fun handleMissingDataException(ex: MissingDataException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        logError(ex)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handleResourceAlreadyExistsException(ex: ResourceAlreadyExistsException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(HttpStatus.CONFLICT, ex.message)
        logError(ex)
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorResponseDto> {
        val errorResponse = ErrorResponseDto(HttpStatus.NOT_FOUND, ex.message)
        logError(ex)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }
}