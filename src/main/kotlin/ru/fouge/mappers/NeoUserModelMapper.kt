package ru.fouge.mappers

import ru.fouge.data.models.NeoUserModel
import ru.fouge.models.auth.RegistrationModel

fun RegistrationModel.toNeoUserModel() = NeoUserModel(
    login = login ?: "",
    password = pass ?: ""
)
