package ru.fouge.utils

import ru.fouge.data.models.NeoTwitCommentModel
import ru.fouge.data.models.NeoTwitModel

fun NeoTwitModel.addComment(commentModel: NeoTwitCommentModel) = copy(
    comments = this.comments + commentModel
)
