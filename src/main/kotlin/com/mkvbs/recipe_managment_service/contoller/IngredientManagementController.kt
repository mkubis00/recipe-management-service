package com.mkvbs.recipe_managment_service.contoller

import com.mkvbs.recipe_managment_service.domain.Locale
import com.mkvbs.recipe_managment_service.dto.ErrorResponseDto
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientDto
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientResponseDto
import com.mkvbs.recipe_managment_service.service.IIngredientService
import com.mkvbs.recipe_managment_service.utils.ingredient.toDomain
import com.mkvbs.recipe_managment_service.utils.ingredient.toResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

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

    @Operation(
        summary = "Delete Ingredient REST API",
        description = "REST API to delete ingredient"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status.OK"
            ),
            ApiResponse(
                responseCode = "404",
                description = "HTTP Status NOT_FOUND",
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
    @DeleteMapping("/deleteIngredient/{id}")
    fun deleteIngredient(@PathVariable id: UUID): ResponseEntity<Void> {
        ingredientService.deleteIngredient(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @Operation(
        summary = "Fetch Ingredient REST API",
        description = "REST API to fetch ingredient by ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status.OK"
            ),
            ApiResponse(
                responseCode = "404",
                description = "HTTP Status NOT_FOUND",
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
    @GetMapping("/ingredientById/{id}")
    fun getIngredientById(@Valid @PathVariable id: UUID): ResponseEntity<IngredientResponseDto> {
        val ingredient = ingredientService.getIngredientById(id)
        return ResponseEntity.status(HttpStatus.OK).body(ingredient.toResponseDto())
    }

    @Operation(
        summary = "Get Ingredient REST API",
        description = "REST API to fetch ingredient by name"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status.OK"
            ),
            ApiResponse(
                responseCode = "404",
                description = "HTTP Status NOT_FOUND",
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
    @GetMapping("/ingredientByName/{name}")
    fun getIngredientByName(
        @PathVariable @NotEmpty(message = "Name can not be null or empty")
        @Size(
            min = 3,
            max = 30,
            message = "Name can not be less than 3 and not greater than 30 characters"
        ) name: String,
        @RequestParam locale: Locale
    ): ResponseEntity<IngredientResponseDto> {
        val ingredient = ingredientService.getIngredientByName(name, locale)
        return ResponseEntity.status(HttpStatus.OK).body(ingredient.toResponseDto())
    }
}