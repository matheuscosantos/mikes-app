package br.com.fiap.mikes.application.core.domain.order.valueobject

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderIdException
import java.util.UUID
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class OrderId private constructor(val value: String) {

    companion object {
        fun new(value: String): Result<OrderId> {
            val uuid = runCatching { UUID.fromString(value) }
                .getOrElse { return failure(InvalidOrderIdException("invalid order id.")) }

            return success(OrderId(uuid.toString()))
        }

        fun generate() = OrderId(UUID.randomUUID().toString())
    }
}
