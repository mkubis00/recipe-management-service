package com.mkvbs.recipe_management_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Schema(
    name = "Error response",
    description = "Schema to hold error response information"
)
data class ErrorResponseDto(

    @field:Schema(
        description = "Error Http code",
        example = "INTERNAL_SERVER_ERROR"
    )
    val errorCode: HttpStatus,

    @field:Schema(
        description = "Message with the error description",
        example = "No ingredient found with ID xxx"
    )
    val errorMessage: String,

    @field:Schema(
        description = "TimeStamp of the exception",
        example = "2025-10-27T09:27:00.834508"
    )
    val timestamp: LocalDateTime = LocalDateTime.now()
)