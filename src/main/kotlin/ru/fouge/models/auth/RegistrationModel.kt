package ru.fouge.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationModel(
    val login: String? = null,
    val pass: String? = null
)
