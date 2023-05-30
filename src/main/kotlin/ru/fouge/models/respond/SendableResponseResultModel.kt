package ru.fouge.models.respond

@kotlinx.serialization.Serializable
data class SendableResponseResultModel<T>(
    val data: T?,
    val error: SendableErrorModel?
)
