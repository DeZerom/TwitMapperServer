package ru.fouge.models.respond

sealed interface DomainRespondResult<out T> {

    class Success<T>(val data: T): DomainRespondResult<T>

    enum class Error(val errorName: String, val description: String?): DomainRespondResult<Nothing> {
        //auth
        NON_VALID_CREDENTIALS("non_valid_credentials", "Логин или пароль пуст"),
        ALREADY_EXISTING_ACCOUNT("already_existing_account", "Аккаунт с таким логином уже существует"),
        WRONG_CREDENTIALS("wrong_credentials", "Неверный логин/пароль"),

        //general
        SOMETHING_WENT_WRONG("something_went_wrong", "Что-то пошло не так"),
        UNAUTHORIZED("unauthorized", "Вы не авторизованы")
    }

}
