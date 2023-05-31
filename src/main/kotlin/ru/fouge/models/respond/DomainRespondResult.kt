package ru.fouge.models.respond

sealed interface DomainRespondResult<out T> {

    class Success<T>(val data: T): DomainRespondResult<T>

    enum class Error(val errorName: String, val description: String?): DomainRespondResult<Nothing> {
        //auth
        NON_VALID_CREDENTIALS("non_valid_credentials", "Логин или пароль пуст"),
        ALREADY_EXISTING_ACCOUNT("already_existing_account", "Аккаунт с таким логином уже существует"),
        WRONG_CREDENTIALS("wrong_credentials", "Неверный логин/пароль"),

        //comment
        WRONG_COMMENT_CREATION_DATA("wrong_creation_data", "Пустой текст комментария или неверно указан родительский твит"),

        //twit
        WRONG_TWIT_ID("wrong_twit_id", "Указан неверный или несуществующий идентефикатор твита"),

        //general
        SOMETHING_WENT_WRONG("something_went_wrong", "Что-то пошло не так"),
        UNAUTHORIZED("unauthorized", "Вы не авторизованы")
    }

}
