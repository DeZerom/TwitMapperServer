package ru.fouge.models.respond

import io.ktor.http.*

data class DomainRespond<T>(
    val code: HttpStatusCode,
    val result: DomainRespondResult<T>
) {
    companion object {
        fun <T> success(data: T): DomainRespond<T> = DomainRespond(
            code = HttpStatusCode.OK,
            result = DomainRespondResult.Success(data = data)
        )
    }
}
