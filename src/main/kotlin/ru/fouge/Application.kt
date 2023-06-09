package ru.fouge

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.fouge.plugins.configureAuth
import ru.fouge.plugins.configureComments
import ru.fouge.plugins.configureSerialization
import ru.fouge.plugins.configureTwits

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureAuth()
    configureTwits()
    configureComments()
}
