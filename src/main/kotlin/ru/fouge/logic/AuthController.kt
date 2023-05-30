package ru.fouge.logic

import io.ktor.http.*
import ru.fouge.data.dao.AuthDao
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.models.respond.DomainRespond
import ru.fouge.models.respond.DomainRespondResult
import ru.fouge.utils.toSha256

object AuthController {

    suspend fun executeRegistration(data: RegistrationModel): DomainRespond<Boolean> {
        if (!validateData(data)) return DomainRespond(
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

    private fun validateData(data: RegistrationModel): Boolean {
        return data.login != null
                && data.pass != null
                && data.login.isNotBlank()
                && data.pass.isNotBlank()
    }

}
