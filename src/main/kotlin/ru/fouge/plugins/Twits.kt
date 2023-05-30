package ru.fouge.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fouge.logic.TwitsController
import ru.fouge.models.twits.CreateTwitModel
import ru.fouge.utils.checkToken
import ru.fouge.utils.getToken
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

        post("/create-twit") {
            if (!call.checkToken()) return@post

            val twit: CreateTwitModel = call.receive()
            val token = call.getToken() ?: return@post
            val result = TwitsController.createTwit(model = twit, token = token)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }
    }
}