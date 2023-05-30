package ru.fouge.models.auth

@kotlinx.serialization.Serializable
data class CredentialsModel(
    val login: String? = null,
    val pass: String? = null
)
