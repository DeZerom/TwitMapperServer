package ru.fouge.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import ru.fouge.logic.AuthController
import ru.fouge.models.respond.DomainRespondResult
import java.security.MessageDigest

fun String.toSha256(): String {
    val md = MessageDigest.getInstance("SHA-256")

    return md.digest(toByteArray()).fold("") {str, it ->
        str + "%02x".format(it)
    }
}

suspend fun ApplicationCall.checkToken(): Boolean {
    val token = getToken()

    val result = AuthController.checkToken(token)
    if (!result) {
        respond(
            status = HttpStatusCode.Unauthorized,
            message = DomainRespondResult.Error.UNAUTHORIZED.toSendable()
        )
    }

    return result
}

fun ApplicationCall.getToken(): String? = request.headers[AUTH_HEADER_KEY]

private const val AUTH_HEADER_KEY = "auth"
