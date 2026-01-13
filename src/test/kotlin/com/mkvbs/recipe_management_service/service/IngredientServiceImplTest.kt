package com.mkvbs.recipe_management_service.service

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_management_service.exception.ResourceAlreadyExistsException
import com.mkvbs.recipe_management_service.exception.ResourceNotFoundException
import com.mkvbs.recipe_management_service.repository.IngredientRepository
import com.mkvbs.recipe_management_service.repository.IngredientTranslationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.util.*

@SpringBootTest
class IngredientServiceImplTest {

    @MockitoBean
    private lateinit var ingredientRepository: IngredientRepository

    @MockitoBean
    private lateinit var translationRepository: IngredientTranslationRepository

    @Autowired
    private lateinit var ingredientService: IngredientServiceImpl

    private val enName = "Sugar"
    private val plName = "Cukier"
    private val enLocale = Locale.en_US
    private val plLocale = Locale.pl_PL

    private val ingredient = createIngredient()
    private val ingredientEntity = createIngredientEntity()

    @Test
    fun `addIngredient should save ingredient when translations are unique`() {
        whenever(translationRepository.findAllByNameIn(any())).thenReturn(
            listOf()
        )
        whenever(ingredientRepository.save(any())).thenReturn(ingredientEntity)

        val result = ingredientService.addIngredient(ingredient)

        assertEquals(enName, result.translations.first().name)
        assertEquals(enLocale, result.translations.first().locale)
        assertEquals(2, result.translations.size)
        assertEquals(2, result.portions.size)
        assertNotNull(result.id)

        Mockito.verify(translationRepository, Mockito.times(1))
            .findAllByNameIn(any())
        Mockito.verify(ingredientRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `addIngredient should throw ResourceAlreadyExistsException for duplicate translation`() {
        whenever(
            translationRepository.findAllByNameIn(
                any()
            )
        ).thenReturn(
            listOf(
                IngredientTranslationEntity(
                    UUID.randomUUID(),
                    plLocale,
                    plName,
                    Mockito.mock(IngredientEntity::class.java)
                )
            )
        )

        val exception = assertThrows<ResourceAlreadyExistsException> {
            ingredientService.addIngredient(ingredient)
        }

        assertEquals("Translations already exist: name: $plName, locale: $plLocale", exception.message)

        Mockito.verify(translationRepository, Mockito.times(1))
            .findAllByNameIn(any())
        Mockito.verify(ingredientRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `deleteIngredient should delete existing ingredient`() {
        val id = UUID.randomUUID()

        whenever(ingredientRepository.findById(id)).thenReturn(Optional.of(Mockito.mock(IngredientEntity::class.java)))

        ingredientService.deleteIngredient(id)

        Mockito.verify(ingredientRepository, Mockito.times(1)).findById(id)
        Mockito.verify(ingredientRepository, Mockito.times(1)).deleteById(id)
    }

    @Test
    fun `deleteIngredient should throw ResourceNotFoundException when ingredient does not exist`() {
        val id = UUID.randomUUID()

        whenever(ingredientRepository.findById(id)).thenReturn(Optional.empty())

        val exception = assertThrows<ResourceNotFoundException> {
            ingredientService.deleteIngredient(id)
        }

        assertEquals("Ingredient with id $id does not exist", exception.message)

        Mockito.verify(ingredientRepository, Mockito.times(1)).findById(id)
        Mockito.verify(ingredientRepository, Mockito.times(0)).deleteById(id)
    }

    @Test
    fun `getIngredientById should return ingredient when it exists`() {
        val id = ingredientEntity.id!!

        whenever(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredientEntity))

        val result = ingredientService.getIngredientById(id)

        assertEquals(id, result.id)
    }

    @Test
    fun `getIngredientById should throw ResourceNotFoundException when ingredient does not exist`() {
        val id = UUID.randomUUID()

        whenever(ingredientRepository.findById(id)).thenReturn(Optional.empty())

        val exception = assertThrows<ResourceNotFoundException> {
            ingredientService.getIngredientById(id)
        }

        assertEquals("Ingredient with id $id does not exist", exception.message)
    }

    @Test
    fun `getIngredientByName should return ingredient when translation exists`() {
        val id = ingredientEntity.id!!

        whenever(ingredientRepository.findByNameAndLocale(any(), any())).thenReturn(Optional.of(ingredientEntity))

        val result = ingredientService.getIngredientByName(enName, enLocale)

        assertEquals(enName, result.translations.first().name)
        assertEquals(id, result.id)
    }

    @Test
    fun `getIngredientByName should throw ResourceNotFoundException when translation does not exist`() {

        whenever(ingredientRepository.findByNameAndLocale(any(), any())).thenReturn(Optional.empty())

        val exception = assertThrows<ResourceNotFoundException> {
            ingredientService.getIngredientByName(enName, enLocale)
        }

        assertEquals("Ingredient with name $enName and locale $enLocale does not exist", exception.message)
    }

    private fun createIngredient(): Ingredient {
        val ingredientTranslations = listOf(
            IngredientTranslation(null, enLocale, enName),
            IngredientTranslation(null, plLocale, plName),
        )
        val ingredientPortions = listOf(
            IngredientPortion(null, PortionType.CUP, 1.0, 10.0, 20.0, 30.0),
            IngredientPortion(null, PortionType.TEASPOON, 2.0, 10.2, 20.2, 30.2)
        )

        return Ingredient.create(
            null,
            ingredientTranslations, ingredientPortions
        )

    }

    private fun createIngredientEntity(): IngredientEntity {
        val ingredientEntity = IngredientEntity(
            UUID.randomUUID(),
            mutableSetOf(),
            mutableSetOf()
        )

        val ingredientTranslationsEntities = mutableSetOf(
            IngredientTranslationEntity(UUID.randomUUID(), enLocale, enName, ingredientEntity),
            IngredientTranslationEntity(UUID.randomUUID(), plLocale, plName, ingredientEntity),
        )
        val ingredientPortionsEntity = mutableSetOf(
            IngredientPortionEntity(
                UUID.randomUUID(),
                PortionType.CUP,
                1.0,
                10.0,
                20.0,
                30.0,
                ingredientEntity
            ),
            IngredientPortionEntity(
                UUID.randomUUID(),
                PortionType.TEASPOON,
                2.0, 10.2,
                20.2,
                30.2,
                ingredientEntity
            )
        )

        ingredientEntity.translations.addAll(ingredientTranslationsEntities)
        ingredientEntity.portions.addAll(ingredientPortionsEntity)

        return ingredientEntity
    }
}