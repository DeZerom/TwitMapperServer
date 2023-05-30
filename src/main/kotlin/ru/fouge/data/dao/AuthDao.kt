package ru.fouge.data.dao

import org.neo4j.driver.Query
import ru.fouge.data.db.NeoDB
import ru.fouge.models.auth.RegistrationModel

object AuthDao {

    suspend fun registerUser(data: RegistrationModel): Boolean {
        val query = Query("CREATE (:User {login: \"${data.login}\", pass: \"${data.pass}\"})")

        return NeoDB.executeWriteQuery(query)
    }

}
