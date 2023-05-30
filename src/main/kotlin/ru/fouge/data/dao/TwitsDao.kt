package ru.fouge.data.dao

import ru.fouge.data.db.NeoDB
import ru.fouge.data.models.NeoTwitModel
import ru.fouge.mappers.toInternal
import ru.fouge.models.twits.TwitModel

object TwitsDao {

    suspend fun getAll(): List<TwitModel>? {
        return NeoDB.executeQueryWithResult {
            loadAll(NeoTwitModel::class.java).map { it.toInternal() }
        }
    }

}
