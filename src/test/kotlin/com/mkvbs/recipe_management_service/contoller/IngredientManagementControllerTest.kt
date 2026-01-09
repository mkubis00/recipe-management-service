package com.mkvbs.recipe_management_service.contoller

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientDto
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientPortionDto
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientTranslationDto
import com.mkvbs.recipe_management_service.exception.FymException
import com.mkvbs.recipe_management_service.exception.ResourceAlreadyExistsException
import com.mkvbs.recipe_management_service.exception.ResourceNotFoundException
import com.mkvbs.recipe_management_service.service.IIngredientService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*


@WebMvcTest(controllers = [IngredientManagementController::class])
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class IngredientManagementControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jsonTester: JacksonTester<IngredientDto>

    @MockitoBean
    private lateinit var ingredientService: IIngredientService

    private val ingredientId = UUID.randomUUID()
    private val ingredientName = "Sugar"
    private val locale = Locale.en_US

    private val translation = IngredientTranslation(id = ingredientId, locale, ingredientName)
    private val translationToSave = IngredientTranslationDto(locale, ingredientName)

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

    private val portionToSave = IngredientPortionDto(
        type = PortionType.GRAM,
        calories = calories,
        fat = fat,
        carbohydrates = carbohydrates,
        protein = protein
    )

    private val ingredient = Ingredient.create(
        ingredientId,
        listOf(translation),
        listOf(portion)
    )

    private val ingredientToSave = IngredientDto(
        translations = listOf(translationToSave),
        portions = listOf(portionToSave)
    )

    @Test
    fun `should create ingredient`() {
        whenever(ingredientService.addIngredient(any())).thenReturn(ingredient)

        val result = mockMvc.post("/api/ingredient/v1/addIngredient") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonTester.write(ingredientToSave).json
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andExpect { jsonPath("$.id").value(ingredientId.toString()) }
            .andReturn()

        assertNotNull(result.response.contentAsString)
    }

    @Test
    fun `should return 409 when ingredient already exists`() {
        whenever(ingredientService.addIngredient(any())).thenThrow(ResourceAlreadyExistsException("Translations already exist: name: $ingredientName, locale: $locale"))

        val result = mockMvc.post("/api/ingredient/v1/addIngredient") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonTester.write(ingredientToSave).json
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isConflict() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        assertNotNull(result.response.contentAsString)
    }

    @Test
    fun `should return 400 when some of data is not valid for create`() {
        val translation = IngredientTranslationDto(locale, "No")
        val ingredientToSave = IngredientDto(
            translations = listOf(translation),
            portions = listOf(portionToSave)
        )

        val result = mockMvc.post("/api/ingredient/v1/addIngredient") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonTester.write(ingredientToSave).json
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        assertNotNull(result.response.contentAsString)
    }

    @Test
    fun `should return 400 when some translations are duplicated in request for create`() {
        val ingredientToSave = IngredientDto(
            translations = listOf(translationToSave, translationToSave),
            portions = listOf(portionToSave)
        )

        val result = mockMvc.post("/api/ingredient/v1/addIngredient") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonTester.write(ingredientToSave).json
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isInternalServerError() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        assertNotNull(result.response.contentAsString)
    }

    @Test
    fun `should return 400 when portions are duplicated in request for create`() {
        val ingredientToSave = IngredientDto(
            translations = listOf(translationToSave),
            portions = listOf(portionToSave, portionToSave)
        )

        val result = mockMvc.post("/api/ingredient/v1/addIngredient") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonTester.write(ingredientToSave).json
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isInternalServerError() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        assertNotNull(result.response.contentAsString)
    }

    @Test
    fun `should delete ingredient by ID when ID is valid and exists`() {
        mockMvc.delete("/api/ingredient/v1/deleteIngredient/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
        }.andReturn()

        verify(ingredientService).deleteIngredient(any())
    }

    @Test
    fun `should return 404 when ID is valid and not exists`() {
        whenever(ingredientService.deleteIngredient(ingredientId)).thenThrow(ResourceNotFoundException("Ingredient not found"))

        mockMvc.delete("/api/ingredient/v1/deleteIngredient/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }.andReturn()
    }

    @Test
    fun `should return 400 when ID is not valid for delete`() {
        mockMvc.delete("/api/ingredient/v1/deleteIngredient/badId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
        }.andReturn()
    }

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
    fun `should return 500 for get by id on internal server error`() {
        val ingredientId = UUID.randomUUID()

        whenever(ingredientService.getIngredientById(any())).thenThrow(FymException("Internal error"))

        mockMvc.get("/api/ingredient/v1/ingredientById/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isInternalServerError() }
        }
    }

    @Test
    fun `should return ingredient by name and local when they are valid and exists`() {
        whenever(ingredientService.getIngredientByName(ingredientName, locale)).thenReturn(ingredient)

        val result = mockMvc.get("/api/ingredient/v1/ingredientByName/$ingredientName?locale=$locale") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andExpect {
            jsonPath("$.translations[0].name").value(ingredientName)
        }.andExpect { jsonPath("$.translations[0].locale").value(locale.toString()) }
            .andReturn()
        assertNotNull(result.response.contentAsString)
    }

    @Test
    fun `should return 404 when name or and local are not found`() {
        whenever(ingredientService.getIngredientByName("notExistingName", locale))
            .thenThrow(ResourceNotFoundException("Ingredient with name notExistingName and locale $locale does not exist"))

        mockMvc.get("/api/ingredient/v1/ingredientByName/notExistingName?locale=$locale") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 400 when name is invalid`() {
        mockMvc.get("/api/ingredient/v1/ingredientByName/no?locale=$locale") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should return 400 when local is invalid`() {
        mockMvc.get("/api/ingredient/v1/ingredientByName/non?locale=non_NON") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should return 500 for get by name on internal server error`() {
        whenever(ingredientService.getIngredientByName(ingredientName, locale))
            .thenThrow(FymException("Internal server error"))

        mockMvc.get("/api/ingredient/v1/ingredientByName/$ingredientName?locale=$locale") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isInternalServerError() }
        }
    }
}
