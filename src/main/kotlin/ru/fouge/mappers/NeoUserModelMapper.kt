package ru.fouge.mappers

import ru.fouge.data.models.NeoUserModel
import ru.fouge.models.auth.ExtendedUserModel
import ru.fouge.models.auth.RegistrationModel
import ru.fouge.models.auth.UserModel
import ru.fouge.utils.toSha256

fun RegistrationModel.toNeoUserModel(token: String = "$login$pass".toSha256()) = NeoUserModel(
    login = login ?: "",
    password = pass ?: "",
    token = token
)

fun NeoUserModel.toInternal() = UserModel(
    id = id ?: 0,
    login = login ?: ""
)

fun NeoUserModel.toExtendedInternal() = ExtendedUserModel(
    id = id ?: 0,
    login = login ?: "",
    isAdmin = isAdmin
)
