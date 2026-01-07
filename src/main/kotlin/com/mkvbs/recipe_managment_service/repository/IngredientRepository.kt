package com.mkvbs.recipe_managment_service.repository

import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IngredientRepository : JpaRepository<IngredientEntity, UUID>
