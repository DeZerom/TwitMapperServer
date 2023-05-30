package ru.fouge.logic

import io.ktor.http.*
import ru.fouge.data.dao.AuthDao
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.models.respond.DomainRespond
import ru.fouge.models.respond.DomainRespondResult

object AuthController {

    suspend fun proceedRegistration(data: RegistrationModel): DomainRespond<Boolean> {
        if (!validateData(data)) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.NON_VALID_CREDENTIALS
        )

        val result = AuthDao.registerUser(data)

        return DomainRespond.success(result)
    }

    private fun validateData(data: RegistrationModel): Boolean {
        return data.login != null
                && data.pass != null
                && data.login.isNotBlank()
                && data.pass.isNotBlank()
    }

}
