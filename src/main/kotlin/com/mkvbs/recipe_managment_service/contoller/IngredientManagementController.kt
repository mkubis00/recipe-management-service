package com.mkvbs.recipe_managment_service.contoller

import com.mkvbs.recipe_managment_service.dto.ErrorResponseDto
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientDto
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientResponseDto
import com.mkvbs.recipe_managment_service.service.IIngredientService
import com.mkvbs.recipe_managment_service.utlis.ingredient.toDomain
import com.mkvbs.recipe_managment_service.utlis.ingredient.toResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "CRUD REST APIs for ingredients management",
    description = "Rest Api to create, delete and fetch full data of ingredients"
)
@RestController
@RequestMapping("/api/ingredient/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class IngredientManagementController(
    val ingredientService: IIngredientService
) {

    @Operation(
        summary = "Create Ingredient REST API",
        description = "REST API to create new ingredient"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "HTTP Status CREATED"
            ),
            ApiResponse(
                responseCode = "409",
                description = "HTTP Status CONFLICT",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status INTERNAL_SERVER_ERROR",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDto::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/addIngredient")
    fun addIngredient(@Valid @RequestBody ingredientDto: IngredientDto): ResponseEntity<IngredientResponseDto> {
        val ingredientToSave = ingredientDto.toDomain()
        val savedIngredient = ingredientService.addIngredient(ingredientToSave)
        val savedIngredientDto = savedIngredient.toResponseDto()
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredientDto)
    }
}