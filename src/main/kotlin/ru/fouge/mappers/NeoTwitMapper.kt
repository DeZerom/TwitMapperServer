package ru.fouge.mappers

import ru.fouge.data.models.NeoTwitModel
import ru.fouge.data.models.NeoUserModel
import ru.fouge.models.twits.CreateTwitModel
import ru.fouge.models.twits.TwitModel

fun CreateTwitModel.toNeo(author: NeoUserModel) = NeoTwitModel(
    lat = lat,
    lon = lon,
    text = text,
    author = author
)

fun NeoTwitModel.toInternal() = TwitModel(
    id = id ?: 0,
    lat = lat ?: .0,
    lon = lon ?: .0,
    author = author?.toInternal()
)