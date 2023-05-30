package ru.fouge.models.respond

@kotlinx.serialization.Serializable
data class SendableErrorModel(
    val name: String,
    val description: String?
)
