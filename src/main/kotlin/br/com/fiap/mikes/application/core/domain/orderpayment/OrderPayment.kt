package br.com.fiap.mikes.application.core.domain.orderpayment

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentId
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentStatus
import java.time.LocalDateTime

class OrderPayment(
    val id: OrderPaymentId,
    val orderId: OrderId,
    val orderPaymentStatus: OrderPaymentStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun new(
            id: OrderPaymentId,
            orderId: OrderId,
            orderPaymentStatus: OrderPaymentStatus,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
        ): Result<OrderPayment> {
            return Result.success(OrderPayment(id, orderId, orderPaymentStatus, createdAt, updatedAt))
        }
    }

    fun payment(paid: Boolean): OrderPayment {
        val updatedAt = LocalDateTime.now()

        val status = when (paid) {
            true -> OrderPaymentStatus.ACCEPTED
            else -> OrderPaymentStatus.REFUSED
        }

        return OrderPayment(
            id,
            orderId,
            status,
            createdAt,
            updatedAt,
        )
    }
}
