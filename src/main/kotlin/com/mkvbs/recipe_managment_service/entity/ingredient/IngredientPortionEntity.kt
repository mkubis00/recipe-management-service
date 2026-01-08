package com.mkvbs.recipe_managment_service.entity.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.PortionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.*


@Entity(name = "portion")
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = "uq_portion_type_ingredient",
            columnNames = ["type", "ingredient_id"]
        )
    ]
)
class IngredientPortionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(nullable = false)
    val type: PortionType,

    @Column(nullable = false)
    val calories: Double,

    @Column(nullable = false)
    val fat: Double,

    @Column(nullable = false)
    val carbohydrates: Double,

    @Column(nullable = false)
    val protein: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    val ingredient: IngredientEntity?
)
