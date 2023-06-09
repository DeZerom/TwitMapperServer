package ru.fouge.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fouge.logic.AuthController
import ru.fouge.models.auth.CredentialsModel
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.utils.*

fun Application.configureAuth() {
    routing {
        options("/reg") { call.processOptionsCall(HttpMethod.Post) }

        post("/reg") {
            call.addQueryHeader()

            val data: RegistrationModel = call.receive()
            val result = AuthController.executeRegistration(data)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        options("/login") { call.processOptionsCall(HttpMethod.Post) }

        post("/login") {
            call.addQueryHeader()

            val data: CredentialsModel = call.receive()
            val result = AuthController.executeLogin(data)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        options("/getMe") { call.processOptionsCall(HttpMethod.Get) }

        get("/getMe") {
            if (!call.checkToken()) return@get
            call.addQueryHeader()

            val result = AuthController.getUserByToken(call.getToken())

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }
    }
}
