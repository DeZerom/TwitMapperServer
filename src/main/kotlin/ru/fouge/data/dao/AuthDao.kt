package ru.fouge.data.dao

import ru.fouge.data.db.NeoDB
import ru.fouge.mappers.toNeoUserModel
import ru.fouge.models.auth.RegistrationModel

object AuthDao {

    suspend fun registerUser(data: RegistrationModel): Boolean {
        val userModel = data.toNeoUserModel()

        return NeoDB.executeQuery { save(userModel) }
    }

}
