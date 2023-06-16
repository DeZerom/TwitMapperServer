# TwitMapperServer
Инструкция по установке

1. Открыть intelliJ IDEA
2. Создать новый проект из под Version Control System (vcs), ссылка на репозиторий - https://github.com/DeZerom/TwitMapperServer.git
3. Подождать, пока все нужные библиотеки скачаются
4. Создать в директории src/main/kotlin/ru/fouge/data/db объект вида (использовать username/password полученные при создании БД)
  object AuthTokenObj {
    fun getToken(): AuthToken = AuthTokens.basic(
        "username",
        "password"
    )
  }
5. Запустить проект (функция main в файле src/main/kotlin/ru/fouge/Application.kt)
