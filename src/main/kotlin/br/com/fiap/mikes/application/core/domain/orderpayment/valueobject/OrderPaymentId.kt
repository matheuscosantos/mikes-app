package br.com.fiap.mikes.application.core.domain.orderpayment.valueobject

import br.com.fiap.mikes.application.core.domain.exception.orderpayment.InvalidOrderPaymentIdException
import java.util.UUID

@JvmInline
value class OrderPaymentId private constructor(val value: String) {

    companion object {
        fun new(value: String): Result<OrderPaymentId> {
            val uuid = runCatching { UUID.fromString(value) }
                .getOrElse { return Result.failure(InvalidOrderPaymentIdException("invalid order payment id.")) }

            return Result.success(OrderPaymentId(uuid.toString()))
        }

        fun generate() = OrderPaymentId(UUID.randomUUID().toString())
    }
}
