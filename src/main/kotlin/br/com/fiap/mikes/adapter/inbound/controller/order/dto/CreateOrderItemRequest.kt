package br.com.fiap.mikes.adapter.inbound.controller.order.dto

import br.com.fiap.mikes.application.port.inbound.order.dto.CreateOrderItemInboundRequest

data class CreateOrderItemRequest(
    val productId: String,
    val quantity: Long,
) {

    fun toInbound(): CreateOrderItemInboundRequest {
        return CreateOrderItemInboundRequest(
            productId,
            quantity,
        )
    }
}
