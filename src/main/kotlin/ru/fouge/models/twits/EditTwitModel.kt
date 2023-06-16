package ru.fouge.models.twits

import kotlinx.serialization.Serializable

@Serializable
data class EditTwitModel(
    val id: Long? = null,
    val text: String? = null
)
