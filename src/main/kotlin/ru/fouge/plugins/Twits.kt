package ru.fouge.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.fouge.utils.checkToken

fun Application.configureTwits() {
    routing {
        get("/twits") {
            if (!call.checkToken()) return@get


        }
    }
}