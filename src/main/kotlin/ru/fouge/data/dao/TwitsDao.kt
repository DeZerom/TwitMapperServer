package ru.fouge.data.dao

import ru.fouge.data.db.NeoDB
import ru.fouge.data.models.NeoTwitCommentModel
import ru.fouge.data.models.NeoTwitModel
import ru.fouge.data.models.NeoUserModel
import ru.fouge.mappers.toInternal
import ru.fouge.mappers.toNeo
import ru.fouge.models.twits.CreateTwitModel
import ru.fouge.models.twits.TwitModel
import ru.fouge.utils.addComment

object TwitsDao {

    suspend fun deleteTwitWithComments(twit: NeoTwitModel): Boolean {
        return NeoDB.executeQuery {
            delete(twit.comments)
            delete(twit)
        }
    }

    suspend fun getById(id: Long): NeoTwitModel? {
        return NeoDB.executeQueryWithResult { load(NeoTwitModel::class.java, id, TWIT_LOADING_DEPTH) }
    }

    suspend fun createRelation(twit: NeoTwitModel, comment: NeoTwitCommentModel): Boolean {
        val newTwitModel = twit.addComment(comment)

        return NeoDB.executeQuery {
            save(newTwitModel)
        }
    }

    suspend fun createTwit(twit: CreateTwitModel, author: NeoUserModel): Boolean {
        val neoTwitModel = twit.toNeo(author = author)

        return NeoDB.executeQuery { save(neoTwitModel) }
    }

    suspend fun getAll(): List<TwitModel>? {
        return NeoDB.executeQueryWithResult {
            loadAll(NeoTwitModel::class.java).map { it.toInternal() }
        }
    }


    private const val TWIT_LOADING_DEPTH = 2
}
