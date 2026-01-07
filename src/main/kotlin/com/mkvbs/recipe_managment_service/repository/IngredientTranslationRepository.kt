package com.mkvbs.recipe_managment_service.repository

import com.mkvbs.recipe_managment_service.domain.Locale
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientTranslationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IngredientTranslationRepository : JpaRepository<IngredientTranslationEntity, UUID> {

    fun existsIngredientTranslationEntitiesByNameAndLocale(name: String, locale: Locale): Boolean
    fun findByNameAndLocale(name: String, locale: Locale): Optional<IngredientTranslationEntity>
}