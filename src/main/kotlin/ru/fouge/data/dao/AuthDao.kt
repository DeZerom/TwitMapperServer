package ru.fouge.data.dao

import org.neo4j.ogm.cypher.BooleanOperator
import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.cypher.Filters
import ru.fouge.data.db.NeoDB
import ru.fouge.data.models.NeoUserModel
import ru.fouge.mappers.toInternal
import ru.fouge.mappers.toNeoUserModel
import ru.fouge.models.auth.AuthRespondModel
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.models.auth.UserModel

object AuthDao {

    suspend fun checkToken(token: String): Boolean {
        val user = getNeoUserByToken(token = token)

        return user != null
    }

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

    suspend fun getAuthInfoByLoginAndPassword(login: String, password: String): AuthRespondModel? {
        val user = NeoDB.executeQueryWithResult {
            loadAll(
                NeoUserModel::class.java,
                Filters(
                    Filter(LOGIN_PROP_NAME, ComparisonOperator.EQUALS, login),
                    Filter(PASS_PROP_NAME, ComparisonOperator.EQUALS, password).apply {
                        booleanOperator = BooleanOperator.AND
                    }
                )
            )?.firstOrNull()
        }

        return if (user?.token != null)
            AuthRespondModel(token = user.token, isAdmin = user.isAdmin)
        else
            null
    }

    suspend fun getNeoUserByToken(token: String): NeoUserModel? {
        return NeoDB.executeQueryWithResult {
            loadAll(
                NeoUserModel::class.java,
                Filter(TOKEN_PROP_NAME, ComparisonOperator.EQUALS, token)
            )?.firstOrNull()
        }
    }

    private const val LOGIN_PROP_NAME = "login"
    private const val PASS_PROP_NAME = "password"
    private const val TOKEN_PROP_NAME = "token"
}
