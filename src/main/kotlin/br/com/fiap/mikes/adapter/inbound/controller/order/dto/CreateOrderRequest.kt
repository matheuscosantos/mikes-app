package br.com.fiap.mikes.adapter.inbound.controller.order.dto

import br.com.fiap.mikes.application.port.inbound.order.dto.CreateOrderInboundRequest

data class CreateOrderRequest(
    val cpf: String?,
    val items: List<CreateOrderItemRequest>,
) {

    fun toInbound(): CreateOrderInboundRequest {
        return CreateOrderInboundRequest(
            cpf,
            items.map { it.toInbound() },
        )
    }
}
