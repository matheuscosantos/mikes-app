package br.com.fiap.mikes.application.core.domain.customer.valueobject

import br.com.fiap.mikes.application.core.domain.exception.customer.InvalidPersonNameException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class PersonName private constructor(val value: String) {

    companion object {

        const val MAX_LENGTH = 255

        fun new(value: String): Result<PersonName> {
            return value.validated()
                .map(::PersonName)
        }

        private fun String.validated(): Result<String> {
            if (isEmpty()) { return failure(InvalidPersonNameException("empty person name.")) }
            if (isTooBig()) { return failure(InvalidPersonNameException("person name cannot be bigger than $MAX_LENGTH.")) }
            if (!isSanitized()) { return failure(InvalidPersonNameException("person name cannot start or end with whitespace.")) }

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
