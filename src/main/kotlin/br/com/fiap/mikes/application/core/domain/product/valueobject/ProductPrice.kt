package br.com.fiap.mikes.application.core.domain.product.valueobject

import br.com.fiap.mikes.application.core.domain.exception.product.InvalidProductPriceException
import java.math.BigDecimal
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class ProductPrice private constructor(val value: BigDecimal) {
    companion object {
        fun new(value: BigDecimal): Result<ProductPrice> {
            if (!value.hasPositivePrice()) { return failure(InvalidProductPriceException("Product Price must be positive.")) }

            return success(ProductPrice(value))
        }

        private fun BigDecimal.hasPositivePrice() = this > BigDecimal.ZERO
    }
}
