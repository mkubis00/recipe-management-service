package com.mkvbs.recipe_management_service.integration

import com.mkvbs.recipe_management_service.contoller.IngredientManagementController
import com.mkvbs.recipe_management_service.repository.IngredientRepository
import com.mkvbs.recipe_management_service.service.IIngredientService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class RecipeManagementServiceApplicationIT {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired(required = false)
    private val ingredientManagementController: IngredientManagementController? = null

    @Autowired(required = false)
    private val ingredientService: IIngredientService? = null

    @Autowired(required = false)
    private val ingredientRepository: IngredientRepository? = null

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var environment: Environment

    @Test
    fun `should load all required beans`() {
        assertThat(environment.activeProfiles).contains("test")

        // Verify controllers
        Assertions.assertNotNull(ingredientManagementController, "IngredientController should be loaded")

        // Verify services
        Assertions.assertNotNull(ingredientService, "IngredientService should be loaded")

        // Verify repositories
        Assertions.assertNotNull(ingredientRepository, "IngredientRepository should be loaded")

        // Verify MockMvc
        Assertions.assertNotNull(mockMvc, "MockMvc should be configured")

        // Print all beans for debugging
        val beanNames = applicationContext.beanDefinitionNames
        println("Total beans loaded: ${beanNames.size}")
        beanNames.forEach { println("Loaded bean: $it") }
    }
}