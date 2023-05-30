package ru.fouge.models.comments

@kotlinx.serialization.Serializable
data class CreateCommentModel(
    val text: String? = null,
    val twitId: Long? = null
)
