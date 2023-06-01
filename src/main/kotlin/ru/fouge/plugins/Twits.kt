package ru.fouge.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fouge.logic.TwitsController
import ru.fouge.models.twits.CreateTwitModel
import ru.fouge.utils.*

fun Application.configureTwits() {
    routing {
        options("/create-twit") { call.processOptionsCall(HttpMethod.Post) }

        post("/create-twit") {
            if (!call.checkToken()) return@post
            call.addQueryHeader()

            val twit: CreateTwitModel = call.receive()
            val token = call.getToken() ?: return@post
            val result = TwitsController.createTwit(model = twit, token = token)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        options("/twits") { call.processOptionsCall(HttpMethod.Get) }

        get("/twits") {
            if (!call.checkToken()) return@get
            call.addQueryHeader()

            val result = TwitsController.getAllTwits()

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        options("/twit") { call.processOptionsCall(HttpMethod.Get) }

        get("/twit") {
            if (!call.checkToken()) return@get
            call.addQueryHeader()

            val twitId = call.parameters[TWIT_ID_FIELD]?.toLongOrNull()

            val result = TwitsController.getTwitDetailsById(twitId)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        options("/delete-twit") { call.processOptionsCall(HttpMethod.Delete) }

        delete("/delete-twit") {
            if (!call.checkToken()) return@delete
            call.addQueryHeader()

            val twitId = call.parameters[TWIT_ID_FIELD]?.toLongOrNull()
            val token = call.getToken()

            val result = TwitsController.deleteTwitById(id = twitId, token = token)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        options("/find-twit") { call.processOptionsCall(HttpMethod.Get) }

        get("/find-twit") {
            if (!call.checkToken()) return@get
            call.addQueryHeader()

            val query = call.parameters[QUERY_FIELD]
            val count = call.parameters[COUNT_FIELD]?.toIntOrNull()

            val result = TwitsController.findTwit(count = count, query = query)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }
    }
}

private const val TWIT_ID_FIELD = "id"
private const val COUNT_FIELD = "count"
private const val QUERY_FIELD = "query"