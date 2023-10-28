package br.com.fiap.mikes.application.core.domain.order.valueobject

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderPriceException
import java.math.BigDecimal
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class OrderPrice private constructor(val value: BigDecimal) {
    companion object {
        fun new(value: BigDecimal): Result<OrderPrice> {
            if (!value.hasPositivePrice()) { return failure(InvalidOrderPriceException("Product Price must be positive.")) }

            return success(OrderPrice(value))
        }

        private fun BigDecimal.hasPositivePrice() = this > BigDecimal.ZERO
    }
}
