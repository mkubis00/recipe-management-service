package com.mkvbs.recipe_management_service.repository

import com.mkvbs.recipe_management_service.entity.ingredient.IngredientTranslationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface IngredientTranslationRepository : JpaRepository<IngredientTranslationEntity, UUID> {
    fun findAllByNameIn(names: List<String>): List<IngredientTranslationEntity>
}