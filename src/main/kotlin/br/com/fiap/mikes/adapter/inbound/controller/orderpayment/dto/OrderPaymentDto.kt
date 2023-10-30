package br.com.fiap.mikes.adapter.inbound.controller.orderpayment.dto

import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import java.time.LocalDateTime

data class OrderPaymentDto(
    val id: String,
    val orderId: String,
    val orderPaymentStatus: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun from(orderPayment: OrderPayment): OrderPaymentDto = with(orderPayment) {
            OrderPaymentDto(
                id.value,
                orderId.value,
                orderPaymentStatus.value,
                createdAt,
                updatedAt,
            )
        }
    }
}
