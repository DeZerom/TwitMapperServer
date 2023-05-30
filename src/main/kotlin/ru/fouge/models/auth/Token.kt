package ru.fouge.models.auth

@kotlinx.serialization.Serializable
data class Token(
    val token: String
)
