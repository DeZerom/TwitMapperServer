package ru.fouge.models.comments

import ru.fouge.models.auth.UserModel

@kotlinx.serialization.Serializable
data class CommentModel(
    val id: Long,
    val author: UserModel?,
    val text: String
)
