package com.mkvbs.recipe_managment_service.contoller

import com.mkvbs.recipe_managment_service.domain.Locale
import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_managment_service.domain.ingredient.PortionType
import com.mkvbs.recipe_managment_service.exception.FymException
import com.mkvbs.recipe_managment_service.exception.ResourceNotFoundException
import com.mkvbs.recipe_managment_service.service.IIngredientService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*


@WebMvcTest(controllers = [IngredientManagementController::class])
@AutoConfigureMockMvc
class IngredientManagementControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var ingredientService: IIngredientService

    private val ingredientId = UUID.randomUUID()
    private val translation = IngredientTranslation(id = ingredientId, locale = Locale.en_US, name = "Sugar")

    private val portionId = UUID.randomUUID()
    private val calories = 100.0
    private val fat = 5.0
    private val carbohydrates = 20.0
    private val protein = 10.0

    private val portion = IngredientPortion(
        id = portionId,
        type = PortionType.GRAM,
        calories = calories,
        fat = fat,
        carbohydrates = carbohydrates,
        protein = protein
    )

    private val ingredient = Ingredient(
        id = ingredientId,
        translations = mutableSetOf(translation),
        portions = mutableSetOf(portion)
    )

    @Test
    fun `should return ingredient by ID when ID is valid and exists`() {
        whenever(ingredientService.getIngredientById(any())).thenReturn(ingredient)

        val result = mockMvc.get("/api/ingredient/v1/ingredientById/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andExpect { jsonPath("$.id").value(ingredientId.toString()) }
            .andReturn()

        assertNotNull(result.response.contentAsString)
    }

    @Test
    fun `should return 400 when ID is not valid`() {
        val notValidId = "notValidId"

        mockMvc.get("/api/ingredient/v1/ingredientById/$notValidId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
        }

        mockMvc.get("/api/ingredient/v1/ingredientById") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 404 when ingredient ID is not found`() {
        val ingredientId = UUID.randomUUID()

        whenever(ingredientService.getIngredientById(ingredientId)).thenThrow(ResourceNotFoundException("Ingredient not found"))

        mockMvc.get("/api/ingredient/v1/ingredientById/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 500 on internal server error`() {
        val ingredientId = UUID.randomUUID()

        whenever(ingredientService.getIngredientById(any())).thenThrow(FymException("Internal error"))

        mockMvc.get("/api/ingredient/v1/ingredientById/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isInternalServerError() }
        }
    }
}