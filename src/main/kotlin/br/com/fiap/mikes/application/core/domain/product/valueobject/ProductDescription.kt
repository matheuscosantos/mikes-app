package br.com.fiap.mikes.application.core.domain.product.valueobject

import br.com.fiap.mikes.application.core.domain.exception.product.InvalidProductDescriptionException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class ProductDescription private constructor(val value: String) {
    companion object {

        const val MAX_LENGTH = 255

        fun new(value: String): Result<ProductDescription> {
            return value.validated()
                .map(::ProductDescription)
        }

        private fun String.validated(): Result<String> {
            if (isEmpty()) { return failure(InvalidProductDescriptionException("empty product description.")) }
            if (isTooBig()) { return failure(InvalidProductDescriptionException("product description cannot be bigger than ${MAX_LENGTH}.")) }
            if (!isSanitized()) { return failure(InvalidProductDescriptionException("product description cannot start or end with whitespace.")) }

            return success(this)
        }

        private fun String.isTooBig(): Boolean {
            return length > MAX_LENGTH
        }

        private fun String.isSanitized(): Boolean {
            return this == trim()
        }
    }
}
