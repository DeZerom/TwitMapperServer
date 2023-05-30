package ru.fouge.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fouge.logic.AuthController
import ru.fouge.models.auth.CredentialsModel
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.utils.toSendable

fun Application.configureAuth() {
    routing {
        post("/reg") {
            val data: RegistrationModel = call.receive()
            val result = AuthController.executeRegistration(data)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        post("/login") {
            val data: CredentialsModel = call.receive()
            val result = AuthController.executeLogin(data)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }
    }
}
