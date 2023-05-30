package ru.fouge.data.dao

import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import ru.fouge.data.db.NeoDB
import ru.fouge.data.models.NeoUserModel
import ru.fouge.mappers.toInternal
import ru.fouge.mappers.toNeoUserModel
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.models.auth.UserModel

object AuthDao {

    suspend fun registerUser(data: RegistrationModel): Boolean {
        val userModel = data.toNeoUserModel()

        return NeoDB.executeQuery { save(userModel) }
    }

    suspend fun getUserByLogin(login: String): UserModel? {
        val result = NeoDB.executeQueryWithResult {
            loadAll(
                NeoUserModel::class.java,
                Filter(LOGIN_PROP_NAME, ComparisonOperator.EQUALS, login)
            )?.firstOrNull()
        }

        return result?.toInternal()
    }

    private const val LOGIN_PROP_NAME = "login"
}