package br.com.fiap.mikes.application.core.domain.order.valueobject

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderItemIdException
import java.util.UUID
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class OrderItemId private constructor(val value: String) {

    companion object {
        fun new(value: String): Result<OrderItemId> {
            val uuid = runCatching { UUID.fromString(value) }
                .getOrElse { return failure(InvalidOrderItemIdException("invalid order item id.")) }

            return success(OrderItemId(uuid.toString()))
        }

        fun generate() = OrderItemId(UUID.randomUUID().toString())
    }
}
