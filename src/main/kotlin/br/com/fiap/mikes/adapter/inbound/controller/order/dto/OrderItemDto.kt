package br.com.fiap.mikes.adapter.inbound.controller.order.dto

import br.com.fiap.mikes.application.core.domain.order.OrderItem
import java.math.BigDecimal

data class OrderItemDto(
    val id: String,
    val productName: String,
    val productPrice: BigDecimal,
    val quantity: Long,
    val price: BigDecimal,
) {

    companion object {
        fun from(orderItem: OrderItem): OrderItemDto = with(orderItem) {
            OrderItemDto(
                id.value,
                productName.value,
                productPrice.value,
                quantity.value,
                price.value
            )
        }
    }
}
