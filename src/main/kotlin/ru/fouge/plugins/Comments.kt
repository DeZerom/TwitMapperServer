package ru.fouge.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.fouge.logic.CommentsController
import ru.fouge.models.comments.CreateCommentModel
import ru.fouge.utils.checkToken
import ru.fouge.utils.getToken
import ru.fouge.utils.toSendable

fun Application.configureComments() {
    routing {
        post("/create-comment") {
            if (!call.checkToken()) return@post

            val commentModel: CreateCommentModel = call.receive()
            val token = call.getToken()

            val result = CommentsController.createComment(commentModel, token)

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }

        delete("/delete-comment") {
            if (!call.checkToken()) return@delete

            val commentId = call.parameters[COMMENT_ID]?.toLongOrNull()

            val result = CommentsController.deleteComment(commentId, call.getToken())

            call.respond(
                status = result.code,
                message = result.result.toSendable()
            )
        }
    }
}

private const val COMMENT_ID = "id"
