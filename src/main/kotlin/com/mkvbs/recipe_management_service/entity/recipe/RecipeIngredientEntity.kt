package com.mkvbs.recipe_management_service.entity.recipe

import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.BatchSize
import java.util.*

@Entity(name = "recipe_ingredient")
@BatchSize(size = 20)
data class RecipeIngredientEntity(
    @Id
    val id: UUID?,

    val amount: Double,
    val ingredientPortionType: PortionType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    val recipe: RecipeEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    val ingredient: IngredientEntity,
)
