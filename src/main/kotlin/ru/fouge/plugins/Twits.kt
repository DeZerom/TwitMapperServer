package ru.fouge.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fouge.logic.TwitsController
import ru.fouge.utils.checkToken
import ru.fouge.utils.toSendable

fun Application.configureTwits() {
    routing {
        get("/twits") {
            if (!call.checkToken()) return@get

            val result = TwitsController.getAllTwits()

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }
    }
}