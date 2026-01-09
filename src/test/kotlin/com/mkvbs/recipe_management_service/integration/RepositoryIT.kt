package com.mkvbs.recipe_management_service.integration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
class RepositoryIT {
    companion object {
        @Container
        val postgres = PostgreSQLContainer("postgres:16")

        @JvmStatic
        @DynamicPropertySource
        fun registerProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.datasource.driver-class-name") { "org.postgresql.Driver" }
        }
    }
}