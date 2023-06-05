package ru.fouge.models.twits

import ru.fouge.models.auth.UserModel

@kotlinx.serialization.Serializable
data class TwitModel(
    val id: Long,
    val author: UserModel?,
    val text: String,
    val lat: Double,
    val lon: Double
)
