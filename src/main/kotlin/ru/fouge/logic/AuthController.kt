package ru.fouge.logic

import io.ktor.http.*
import ru.fouge.data.dao.AuthDao
import ru.fouge.mappers.toInternal
import ru.fouge.models.auth.AuthRespondModel
import ru.fouge.models.auth.CredentialsModel
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.models.auth.UserModel
import ru.fouge.models.respond.DomainRespond
import ru.fouge.models.respond.DomainRespondResult
import ru.fouge.utils.toSha256
import java.util.logging.Level
import java.util.logging.Logger

object AuthController {

    suspend fun getUser(token: String?): DomainRespond<UserModel> {
        if (token == null) return DomainRespond.unauthorized()

        val result = AuthDao.getNeoUserByToken(token)?.toInternal() ?: return DomainRespond.unauthorized()

        return DomainRespond.success(result)
    }

    suspend fun checkToken(token: String?): Boolean {
        if (token == null) return false

        return AuthDao.checkToken(token)
    }

    suspend fun executeLogin(credentials: CredentialsModel): DomainRespond<AuthRespondModel> {
        if (!validateCredentials(credentials)) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.NON_VALID_CREDENTIALS
        )

        val newCredentials = credentials.copy(pass = credentials.pass?.toSha256())
        Logger.getAnonymousLogger().log(Level.WARNING, newCredentials.toString())

        val token = AuthDao.getAuthInfoByLoginAndPassword(
            login = newCredentials.login ?: "",
            password = newCredentials.pass ?: ""
        )

        return if (token != null) {
            DomainRespond.success(token)
        } else {
            DomainRespond(
                code = HttpStatusCode.BadRequest,
                DomainRespondResult.Error.WRONG_CREDENTIALS
            )
        }
    }

    suspend fun executeRegistration(data: RegistrationModel): DomainRespond<Boolean> {
        if (!validateRegistrationModel(data)) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.NON_VALID_CREDENTIALS
        )

        val newData = data.copy(pass = data.pass?.toSha256())

        val user = AuthDao.getUserByLogin(newData.login ?: "")
        if (user != null) return DomainRespond(
            code = HttpStatusCode.Conflict,
            result = DomainRespondResult.Error.ALREADY_EXISTING_ACCOUNT
        )

        val result = AuthDao.registerUser(newData)

        return DomainRespond.success(result)
    }

    private fun validateCredentials(credentials: CredentialsModel): Boolean {
        return credentials.login != null
                && credentials.pass != null
                && credentials.login.isNotBlank()
                && credentials.pass.isNotBlank()
    }

    private fun validateRegistrationModel(data: RegistrationModel): Boolean {
        return data.login != null
                && data.pass != null
                && data.login.isNotBlank()
                && data.pass.isNotBlank()
    }

}
