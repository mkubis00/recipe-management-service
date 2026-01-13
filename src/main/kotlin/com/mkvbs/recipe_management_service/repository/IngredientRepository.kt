package com.mkvbs.recipe_management_service.repository

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface IngredientRepository : JpaRepository<IngredientEntity, UUID> {

    @Query(
        """
    SELECT i FROM ingredient i 
        JOIN i.translations t 
        JOIN FETCH i.translations
        JOIN FETCH i.portions
        WHERE t.name = :name AND t.locale = :locale
    """
    )
    fun findByNameAndLocale(name: String, locale: Locale): Optional<IngredientEntity>

    @Query(
        """
    SELECT i FROM ingredient i 
        JOIN i.translations t 
        JOIN FETCH i.translations
        JOIN FETCH i.portions
        WHERE i.id = :id
    """
    )
    override fun findById(id: UUID): Optional<IngredientEntity>
}
