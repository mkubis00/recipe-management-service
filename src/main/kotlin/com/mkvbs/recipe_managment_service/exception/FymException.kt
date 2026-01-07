package com.mkvbs.recipe_managment_service.exception

/**
 * Base exception class for the application, typically used to represent
 * runtime exceptions with custom error messages.
 *
 * Inherits from [RuntimeException] and can be extended by other specific
 * exception classes to provide more granular exception handling within the
 * application.
 *
 * @param message The detail message explaining the reason for the exception.
 */
open class FymException(
    override val message: String,
) : RuntimeException()