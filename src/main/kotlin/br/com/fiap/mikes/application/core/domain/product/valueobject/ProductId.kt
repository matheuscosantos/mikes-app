package br.com.fiap.mikes.application.core.domain.product.valueobject

import br.com.fiap.mikes.application.core.domain.exception.product.InvalidProductIdException
import java.util.UUID
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class ProductId(val value: String) {
    companion object {
        fun new(value: String): Result<ProductId> {
            val uuid = runCatching { UUID.fromString(value) }
                .getOrElse { return failure(InvalidProductIdException("invalid product id.")) }

            return success(ProductId(uuid.toString()))
        }

        fun generate() = ProductId(UUID.randomUUID().toString())
    }
}