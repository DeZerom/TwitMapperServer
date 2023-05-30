package ru.fouge.logic

import io.ktor.http.*
import ru.fouge.data.dao.AuthDao
import ru.fouge.data.dao.TwitsDao
import ru.fouge.mappers.toNeo
import ru.fouge.models.comments.CreateCommentModel
import ru.fouge.models.respond.DomainRespond
import ru.fouge.models.respond.DomainRespondResult

object CommentsController {

    suspend fun createComment(commentModel: CreateCommentModel, token: String): DomainRespond<Boolean> {
        if (!validateCreateCommentModel(commentModel)) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_COMMETN_CREATION_DATA
        )

        val author = AuthDao.getNeoUserByToken(token) ?: return DomainRespond.unauthorized()

        val neoCommentModel = commentModel.toNeo(author)
        val twit = TwitsDao.getById(commentModel.twitId ?: 0) ?: return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_COMMETN_CREATION_DATA
        )

        val relationRes = TwitsDao.createRelation(
            twit = twit,
            comment = neoCommentModel
        )

        return DomainRespond.success(relationRes)
    }

    private fun validateCreateCommentModel(model: CreateCommentModel): Boolean =
        model.twitId != null
        && !model.text.isNullOrBlank()

}
