package br.com.fiap.mikes.adapter.inbound.controller.order.dto

import br.com.fiap.mikes.application.core.domain.order.Order
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val id: String,
    val number: Long,
    val cpf: String?,
    val orderItems: List<OrderItemDto>,
    val price: BigDecimal,
    val orderStatus: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun from(order: Order): OrderDto = with(order) {
            OrderDto(
                id.value,
                number.value,
                cpf?.value,
                items.map { OrderItemDto.from(it) },
                price.value,
                orderStatus.value,
                createdAt,
                updatedAt,
            )
        }
    }
}
