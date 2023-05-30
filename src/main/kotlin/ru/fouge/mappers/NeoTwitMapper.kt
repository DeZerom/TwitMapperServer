package ru.fouge.mappers

import ru.fouge.data.models.NeoTwitModel
import ru.fouge.models.twits.TwitModel

fun NeoTwitModel.toInternal() = TwitModel(
    id = id ?: 0,
    lat = lat ?: .0,
    lon = lon ?: .0,
    author = author?.toInternal()
)