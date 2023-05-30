package ru.fouge.models.auth

@kotlinx.serialization.Serializable
data class UserModel(
    val id: Long,
    val login: String
)
