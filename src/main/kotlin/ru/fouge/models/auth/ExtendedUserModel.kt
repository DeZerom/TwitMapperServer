package ru.fouge.models.auth

@kotlinx.serialization.Serializable
data class ExtendedUserModel(
    val id: Long,
    val login: String,
    val isAdmin: Boolean
)
