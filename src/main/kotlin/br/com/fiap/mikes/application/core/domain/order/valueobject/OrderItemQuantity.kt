package br.com.fiap.mikes.application.core.domain.order.valueobject

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderItemQuantityException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class OrderItemQuantity private constructor(val value: Long) {
    companion object {
        fun new(value: Long): Result<OrderItemQuantity> {
            if (!value.hasPositiveQuantity()) { return failure(InvalidOrderItemQuantityException("Order item quantity must be positive."))
            }

            return success(OrderItemQuantity(value))
        }

        private fun Long.hasPositiveQuantity() = this > 0
    }
}
