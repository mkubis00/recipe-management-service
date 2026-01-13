package com.mkvbs.recipe_management_service.integration

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_management_service.repository.IngredientRepository
import com.mkvbs.recipe_management_service.service.IIngredientService
import jakarta.persistence.EntityManagerFactory
import org.hibernate.SessionFactory
import org.hibernate.stat.Statistics
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.test.Test

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class IngredientServiceDataBaseQueryIT(
    @Autowired private val entityManagerFactory: EntityManagerFactory,
    @Autowired private val repository: IngredientRepository,
    @Autowired private val ingredientService: IIngredientService
) {
    private val calories = 100.0
    private val fat = 5.0
    private val carbohydrates = 20.0
    private val protein = 10.0
    private val ingredientName = "Sugar"
    private val locale = Locale.en_US
    private lateinit var ingredientId: UUID

    val stats = setUpStats()

    private fun setUpStats(): Statistics {
        val sessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)
        return sessionFactory.statistics
    }

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
        ingredientId = repository.save(ingredient).id !!

        stats.clear()
    }

    @Test
    fun `successful add should execute 4 queries`() {
        val newIngredientName = "newIngredientName"
        val translation = IngredientTranslation(null, locale, newIngredientName)

        val portion = IngredientPortion(
            null,
            type = PortionType.GRAM,
            calories = calories,
            fat = fat,
            carbohydrates = carbohydrates,
            protein = protein,
        )

        val ingredient = Ingredient.create(null, listOf(translation), listOf(portion))

        ingredientService.addIngredient(ingredient)

        val queryCount = stats.prepareStatementCount
        assertEquals(4, queryCount)
    }

    @Test
    fun `successful delete should execute 4 queries`() {
        ingredientService.deleteIngredient(ingredientId)

        val queryCount = stats.prepareStatementCount
        assertEquals(4, queryCount)
    }

    @Test
    fun `successful get by ID should execute 1 queries`() {
        ingredientService.getIngredientById(ingredientId)

        val queryCount = stats.prepareStatementCount
        assertEquals(1, queryCount)
    }

    @Test
    fun `successful get by name and locale should execute 1 queries`() {
        ingredientService.getIngredientByName(ingredientName, locale)

        val queryCount = stats.prepareStatementCount
        assertEquals(1, queryCount)
    }
}