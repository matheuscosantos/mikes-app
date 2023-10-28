package br.com.fiap.mikes.application.core.domain.orderpayment.valueobject

import br.com.fiap.mikes.application.core.domain.exception.orderpayment.InvalidOrderPaymentStatusException

enum class OrderPaymentStatus(val value: String) {
    ACCEPTED("accepted"),
    REFUSED("refused"),
    WAITING("waiting"),
    ;

    companion object {
        fun findByValue(value: String): Result<OrderPaymentStatus> {
            val status = values().firstOrNull { it.value == value }
                ?: return Result.failure(InvalidOrderPaymentStatusException("Invalid Order Payment Status: '$value'."))

            return Result.success(status)
        }
    }
}
