package ru.fouge.utils

import ru.fouge.models.respond.DomainRespondResult
import ru.fouge.models.respond.SendableErrorModel
import ru.fouge.models.respond.SendableResponseResultModel

fun <T> DomainRespondResult<T>.toSendable() = when (this) {
    is DomainRespondResult.Success -> SendableResponseResultModel(
        data = data,
        error = null
    )
    is DomainRespondResult.Error -> SendableResponseResultModel(
        data = null,
        error = SendableErrorModel(
            name = this.errorName,
            description = this.description
        )
    )
}
