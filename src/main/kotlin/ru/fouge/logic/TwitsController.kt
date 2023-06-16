package ru.fouge.logic

import io.ktor.http.*
import me.xdrop.fuzzywuzzy.FuzzySearch
import ru.fouge.data.dao.AuthDao
import ru.fouge.data.dao.TwitsDao
import ru.fouge.mappers.toDetailedInternal
import ru.fouge.mappers.toInternal
import ru.fouge.models.respond.DomainRespond
import ru.fouge.models.respond.DomainRespondResult
import ru.fouge.models.twits.CreateTwitModel
import ru.fouge.models.twits.DetailedTwitModel
import ru.fouge.models.twits.EditTwitModel
import ru.fouge.models.twits.TwitModel

object TwitsController {

    suspend fun editTwit(token: String?, model: EditTwitModel): DomainRespond<Boolean> {
        if (token.isNullOrEmpty()) return DomainRespond.unauthorized()


        if (model.id == null || model.text.isNullOrEmpty()) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_EDIT_PARAMETERS
        )

        val user = AuthDao.getNeoUserByToken(token)
        val twit = TwitsDao.getById(model.id)?.copy(text = model.text) ?: return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_ID
        )

        if (user?.isAdmin == false) {
            if (twit.author != user) {
                return DomainRespond(
                    code = HttpStatusCode.Forbidden,
                    result = DomainRespondResult.Error.NO_RIGHTS
                )
            }
        }

        val result = TwitsDao.editTwit(twit)

        return DomainRespond.success(result)
    }

    suspend fun findTwit(query: String?, count: Int?): DomainRespond<List<TwitModel>> {
        if (query == null || query.isBlank()) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_SEARCH_PARAMETERS
        )

        if (count == null || count <= 0) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_SEARCH_PARAMETERS
        )

        val twits = TwitsDao.getAll()

        val results = FuzzySearch.extractSorted(query, twits?.map { it.text })
            .take(count)
            .mapNotNull {
                twits?.getOrNull(it.index)?.toInternal()
            }

        return DomainRespond.success(results)
    }

    suspend fun deleteTwitById(id: Long?, token: String?): DomainRespond<Boolean> {
        if (id == null) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_ID
        )

        if (token == null) return DomainRespond.unauthorized()

        val user = AuthDao.getNeoUserByToken(token)
        val twit = TwitsDao.getById(id) ?: return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_ID
        )

        if (user?.isAdmin == null || !user.isAdmin) {
            if (twit.author?.id == null || twit.author.id != user?.id) {
                return DomainRespond(
                    code = HttpStatusCode.Forbidden,
                    result = DomainRespondResult.Error.NO_RIGHTS
                )
            }
        }

        val result = TwitsDao.deleteTwitWithComments(twit)

        return DomainRespond.success(result)
    }

    suspend fun getTwitDetailsById(twitId: Long?): DomainRespond<DetailedTwitModel> {
        if (twitId == null) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_ID
        )

        val result = TwitsDao.getById(twitId)?.toDetailedInternal() ?: return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_ID
        )

        return DomainRespond.success(result)
    }

    suspend fun createTwit(model: CreateTwitModel, token: String): DomainRespond<Boolean> {
        if (!validateTwitCreationModel(model)) return DomainRespond(
            code = HttpStatusCode.BadRequest,
            result = DomainRespondResult.Error.WRONG_TWIT_CREATION_DATA
        )

        val author = AuthDao.getNeoUserByToken(token) ?: return DomainRespond.unauthorized()

        val result = TwitsDao.createTwit(twit = model, author = author)

        return DomainRespond.success(result)
    }

    suspend fun getAllTwits(): DomainRespond<List<TwitModel>> {
        val result = TwitsDao.getAll()

        return if (result != null) {
            DomainRespond.success(result.map { it.toInternal() })
        } else {
            DomainRespond(
                code = HttpStatusCode.InternalServerError,
                result = DomainRespondResult.Error.SOMETHING_WENT_WRONG
            )
        }
    }

    private fun validateTwitCreationModel(model: CreateTwitModel): Boolean {
        return model.text != null
                && model.lat != null
                && model.lon != null
                && model.text.isNotBlank()
    }
}
