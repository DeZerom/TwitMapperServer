package ru.fouge.mappers

import ru.fouge.data.models.NeoTwitCommentModel
import ru.fouge.data.models.NeoUserModel
import ru.fouge.models.comments.CreateCommentModel

fun CreateCommentModel.toNeo(author: NeoUserModel) = NeoTwitCommentModel(
    text = text,
    author = author
)