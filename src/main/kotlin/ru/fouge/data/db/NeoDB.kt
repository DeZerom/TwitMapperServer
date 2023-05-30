package ru.fouge.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory

object NeoDB {

    private val driver: Driver = GraphDatabase.driver(
        "neo4j+s://8fe44843.databases.neo4j.io",
        AuthTokenObj.getToken()
    )
    private val sessionFactory = SessionFactory(BoltDriver(driver), "ru.fouge.data.models")

    suspend fun executeQuery(query: Session.() -> Unit): Boolean = withContext(Dispatchers.IO) {
        try {
            sessionFactory.openSession().apply(query)
            true
        } catch (e: Exception) {
            false
        }
    }

}
