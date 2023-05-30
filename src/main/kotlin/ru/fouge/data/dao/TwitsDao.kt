package ru.fouge.data.dao

import ru.fouge.data.db.NeoDB
import ru.fouge.data.models.NeoTwitModel
import ru.fouge.data.models.NeoUserModel
import ru.fouge.mappers.toInternal
import ru.fouge.mappers.toNeo
import ru.fouge.models.twits.CreateTwitModel
import ru.fouge.models.twits.TwitModel

object TwitsDao {

    suspend fun createTwit(twit: CreateTwitModel, author: NeoUserModel): Boolean {
        val neoTwitModel = twit.toNeo(author = author)

        return NeoDB.executeQuery { save(neoTwitModel) }
    }

    suspend fun getAll(): List<TwitModel>? {
        return NeoDB.executeQueryWithResult {
            loadAll(NeoTwitModel::class.java).map { it.toInternal() }
        }
    }

}
