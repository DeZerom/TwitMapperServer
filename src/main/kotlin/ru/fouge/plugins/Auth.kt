package ru.fouge.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fouge.logic.AuthController
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.utils.toSendable

fun Application.configureAuth() {
    routing {
        post("/reg") {
            val data: RegistrationModel = call.receive()
            val result = AuthController.proceedRegistration(data)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }
    }
}
