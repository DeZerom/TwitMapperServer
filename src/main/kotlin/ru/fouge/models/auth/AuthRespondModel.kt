package ru.fouge.models.auth

@kotlinx.serialization.Serializable
data class AuthRespondModel(
    val token: String,
    val isAdmin: Boolean
)
