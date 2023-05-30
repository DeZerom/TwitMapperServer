package ru.fouge.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Query
import org.neo4j.driver.Session

object NeoDB {

    private val driver: Driver = GraphDatabase.driver(
        "neo4j+s://8fe44843.databases.neo4j.io",
        AuthTokenObj.getToken()
    )

    suspend fun executeWriteQuery(query: Query): Boolean = withContext(Dispatchers.IO) {
        val session = driver.session(Session::class.java)

        session.executeWrite {
            try {
                it.run(query)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

}
