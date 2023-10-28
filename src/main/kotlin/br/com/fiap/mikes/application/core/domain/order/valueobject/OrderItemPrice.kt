package br.com.fiap.mikes.application.core.domain.order.valueobject

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderItemPriceException
import java.math.BigDecimal
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class OrderItemPrice private constructor(val value: BigDecimal) {
    companion object {
        fun new(value: BigDecimal): Result<OrderItemPrice> {
            if (!value.hasPositivePrice()) { return failure(InvalidOrderItemPriceException("Order item price must be positive."))
            }

            return success(OrderItemPrice(value))
        }

        private fun BigDecimal.hasPositivePrice() = this > BigDecimal.ZERO
    }
}