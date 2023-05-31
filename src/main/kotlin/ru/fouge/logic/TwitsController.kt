package ru.fouge.logic

import io.ktor.http.*
import ru.fouge.data.dao.AuthDao
import ru.fouge.data.dao.TwitsDao
import ru.fouge.mappers.toDetailedInternal
import ru.fouge.models.respond.DomainRespond
import ru.fouge.models.respond.DomainRespondResult
import ru.fouge.models.twits.CreateTwitModel
import ru.fouge.models.twits.DetailedTwitModel
import ru.fouge.models.twits.TwitModel

object TwitsController {

    suspend fun getTwitDetailsById(twitId: Long): DomainRespond<DetailedTwitModel> {
        val result = TwitsDao.getById(twitId)?.toDetailedInternal() ?: return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_ID
        )

        return DomainRespond.success(result)
    }

    suspend fun createTwit(model: CreateTwitModel, token: String): DomainRespond<Boolean> {
        val author = AuthDao.getNeoUserByToken(token) ?: return DomainRespond.unauthorized()

        val result = TwitsDao.createTwit(twit = model, author = author)

        return DomainRespond.success(result)
    }

    suspend fun getAllTwits(): DomainRespond<List<TwitModel>> {
        val result = TwitsDao.getAll()

        return if (result != null) {
            DomainRespond.success(result)
        } else {
            DomainRespond(
                code = HttpStatusCode.InternalServerError,
                result = DomainRespondResult.Error.SOMETHING_WENT_WRONG
            )
        }
    }

}
