package ru.fouge.data.db

import org.neo4j.driver.AuthToken
import org.neo4j.driver.AuthTokens

object AuthTokenObj {
    fun getToken(): AuthToken = AuthTokens.basic(
        "neo4j",
        "303Ocz0CYUzIyVKmyr3OLW3EormnYzwcYTKhPHDklgs"
    )
}
