package com.mkvbs.recipe_managment_service

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "Recipe Service",
        version = "0.0.1",
        description = "Recipe Service API which provides management of recipes and ingredients",
        contact = Contact(
            name = "Maciej Kubis",
            url = "https://github.com/mkvbs/recipe_management_service",
            email = "maciej.k-2000@outlook.com"
        ),
        license = License(
            name = "Open Source Licenses",
        )
    )
)
class RecipeManagmentServiceApplication

fun main(args: Array<String>) {
    runApplication<RecipeManagmentServiceApplication>(*args)
}
