package com.mkvbs.recipe_management_service.integration

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_management_service.repository.IngredientRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientControllerIT(
    @Autowired private val repository: IngredientRepository,
    @Autowired private val mockMvc: MockMvc,
) {
    private val calories = 100.0
    private val fat = 5.0
    private val carbohydrates = 20.0
    private val protein = 10.0
    private val ingredientName = "Sugar"
    private val locale = Locale.en_US

    private lateinit var ingredientId: String

    @BeforeEach
    fun setUp() {
        val ingredient = IngredientEntity(null, mutableSetOf(), mutableSetOf())

        val translation = IngredientTranslationEntity(null, locale, ingredientName, ingredient)

        val portion = IngredientPortionEntity(
            null,
            type = PortionType.GRAM,
            calories = calories,
            fat = fat,
            carbohydrates = carbohydrates,
            protein = protein,
            ingredient
        )

        ingredient.translations.add(translation)
        ingredient.portions.add(portion)
        ingredientId = repository.save(ingredient).id !!.toString()
    }

    @Test
    fun `should delete ingredient by id`() {
        mockMvc.get("/api/ingredient/v1/ingredientById/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(ingredientId) }
            jsonPath("$.translations[0].name") { value(ingredientName) }
        }

        mockMvc.delete("/api/ingredient/v1/deleteIngredient/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/api/ingredient/v1/ingredientById/3fa85f64-5717-4562-b3fc-2c963f66afa6") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should not delete ingredient by id`() {
        mockMvc.delete("/api/ingredient/v1/deleteIngredient/3fa85f64-5717-4562-b3fc-2c963f66afa6") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should get ingredient by id`() {
        mockMvc.get("/api/ingredient/v1/ingredientById/$ingredientId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(ingredientId) }
            jsonPath("$.translations[0].name") { value(ingredientName) }
        }
    }

    @Test
    fun `should not get ingredient by id`() {
        mockMvc.get("/api/ingredient/v1/ingredientById/3fa85f64-5717-4562-b3fc-2c963f66afa6") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 409 by invalid id`() {
        mockMvc.get("/api/ingredient/v1/ingredientById/3fa85f64-5717") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should get ingredient by name and locale`() {
        mockMvc.get("/api/ingredient/v1/ingredientByName/$ingredientName?locale=$locale") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(ingredientId) }
            jsonPath("$.translations[0].name") { value(ingredientName) }
            jsonPath("$.translations[0].locale") { value(locale.name) }
        }
    }

    @Test
    fun `should not get ingredient by name and locale`() {
        mockMvc.get("/api/ingredient/v1/ingredientByName/notExistingName?locale=$locale") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 409 get by invalid name`() {
        mockMvc.get("/api/ingredient/v1/ingredientByName/no?locale=$locale") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should return 409 get by invalid locale`() {
        mockMvc.get("/api/ingredient/v1/ingredientByName/validName?locale=no_NO") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isBadRequest() }
        }
    }
}