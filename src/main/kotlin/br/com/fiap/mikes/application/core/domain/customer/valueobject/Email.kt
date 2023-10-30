package br.com.fiap.mikes.application.core.domain.customer.valueobject

import br.com.fiap.mikes.application.core.domain.exception.customer.InvalidEmailException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class Email private constructor(val value: String) {

    companion object {

        const val MAX_LENGTH = 255

        private val EMAIL_ADDRESS_PATTERN = Regex(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+",
        )

        fun new(value: String): Result<Email> {
            return value.validated()
                .map(::Email)
        }

        private fun String.validated(): Result<String> {
            if (isEmpty()) { return failure(InvalidEmailException("empty email.")) }
            if (isTooBig()) { return failure(InvalidEmailException("email cannot be bigger than $MAX_LENGTH.")) }
            if (!isValidPattern()) { return failure(InvalidEmailException("invalid email.")) }

            return success(this)
        }

        private fun String.isTooBig(): Boolean {
            return length > MAX_LENGTH
        }

        private fun String.isValidPattern(): Boolean {
            return EMAIL_ADDRESS_PATTERN.matches(this)
        }
    }
}
