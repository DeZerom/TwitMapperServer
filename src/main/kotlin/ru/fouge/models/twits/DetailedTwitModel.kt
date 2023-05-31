package ru.fouge.models.twits

import ru.fouge.models.auth.UserModel
import ru.fouge.models.comments.CommentModel

@kotlinx.serialization.Serializable
data class DetailedTwitModel(
    val id: Long,
    val lat: Double,
    val lon: Double,
    val text: String,
    val author: UserModel?,
    val comments: List<CommentModel>
)
