package br.com.fiap.mikes.application.core.domain.customer.valueobject

import br.com.fiap.mikes.application.core.domain.exception.customer.InvalidCpfException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@JvmInline
value class Cpf private constructor(val value: String) {

    companion object {

        const val LENGTH = 11
        private const val DIGITS_LENGTH = 9
        private const val VALIDATION_LENGTH = 2

        fun new(value: String): Result<Cpf> {
            return value
                .validated()
                .map(::Cpf)
        }

        private fun String.validated(): Result<String> {
            if (!hasValidLength()) { return failure(InvalidCpfException("must have '$LENGTH' length.")) }
            if (!hasOnlyDigits()) { return failure(InvalidCpfException("must have only digits.")) }
            if (isOnBlacklist()) { return failure(InvalidCpfException("blacklist cpf.")) }
            if (!hasValidValidationCode()) { return failure(InvalidCpfException("invalid cpf.")) }

            return success(this)
        }

        private fun String.hasValidLength(): Boolean {
            return LENGTH == length
        }

        private fun String.hasOnlyDigits(): Boolean {
            return all { it.isDigit() }
        }

        private fun String.isOnBlacklist(): Boolean {
            val firstDigit = get(0)
            return all { it == firstDigit }
        }

        private fun String.hasValidValidationCode(): Boolean {
            val digits = take(DIGITS_LENGTH)

            val dozens = calculateValidationDigit(digits)
            val digitsWithFirstValidator = digits + dozens.toString()

            val digit = calculateValidationDigit(digitsWithFirstValidator)

            val expectedValidationDigits = dozens * 10 + digit

            val actualValidation = takeLast(VALIDATION_LENGTH).toInt()

            return expectedValidationDigits == actualValidation
        }

        private fun calculateValidationDigit(digits: String): Int {
            val calculatedValidator = 11 - digits
                .map(Char::digitToInt)
                .mapIndexed { index, digit -> (digits.length - index + 1) * digit }
                .sum()
                .mod(11)

            return if (calculatedValidator < 10) calculatedValidator else 0
        }
    }
}
