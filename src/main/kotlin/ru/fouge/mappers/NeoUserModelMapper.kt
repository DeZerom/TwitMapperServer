package ru.fouge.mappers

import ru.fouge.data.models.NeoUserModel
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.models.auth.UserModel

fun RegistrationModel.toNeoUserModel() = NeoUserModel(
    login = login ?: "",
    password = pass ?: ""
)

fun NeoUserModel.toInternal() = UserModel(
    id = id ?: 0,
    login = login ?: ""
)
