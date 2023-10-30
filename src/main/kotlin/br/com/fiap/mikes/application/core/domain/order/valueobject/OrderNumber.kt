package br.com.fiap.mikes.application.core.domain.order.valueobject

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderNumberException

@JvmInline
value class OrderNumber private constructor(val value: Long) {
    companion object {
        fun new(value: Long): Result<OrderNumber> {
            if (!value.hasPositiveQuantity()) { return Result.failure(InvalidOrderNumberException("Order item quantity must be positive."))
            }

            return Result.success(OrderNumber(value))
        }

        private fun Long.hasPositiveQuantity() = this > 0
    }
}
