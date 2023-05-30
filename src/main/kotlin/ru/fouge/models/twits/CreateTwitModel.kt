package ru.fouge.models.twits

@kotlinx.serialization.Serializable
data class CreateTwitModel(
    val text: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)
