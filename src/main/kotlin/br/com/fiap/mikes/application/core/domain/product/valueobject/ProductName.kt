package br.com.fiap.mikes.application.core.domain.product.valueobject

import br.com.fiap.mikes.application.core.domain.customer.valueobject.PersonName
import br.com.fiap.mikes.application.core.domain.exception.product.InvalidProductNameException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class ProductName private constructor(val value: String) {
    companion object {

        const val MAX_LENGTH = 255

        fun new(value: String): Result<ProductName> {
            return value.validated()
                .map(::ProductName)
        }

        private fun String.validated(): Result<String> {
            if (isEmpty()) { return failure(InvalidProductNameException("empty product name.")) }
            if (isTooBig()) { return failure(InvalidProductNameException("product name cannot be bigger than ${MAX_LENGTH}.")) }
            if (!isSanitized()) { return failure(InvalidProductNameException("product name cannot start or end with whitespace.")) }

            return success(this)
        }

        private fun String.isTooBig(): Boolean {
            return length > PersonName.MAX_LENGTH
        }

        private fun String.isSanitized(): Boolean {
            return this == trim()
        }
    }
}
