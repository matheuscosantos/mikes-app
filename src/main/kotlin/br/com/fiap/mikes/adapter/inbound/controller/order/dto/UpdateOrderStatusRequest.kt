package br.com.fiap.mikes.adapter.inbound.controller.order.dto

import br.com.fiap.mikes.application.port.inbound.order.dto.UpdateOrderStatusInboundRequest

data class UpdateOrderStatusRequest(
    val status: String
) {

    fun toInbound(): UpdateOrderStatusInboundRequest {
        return UpdateOrderStatusInboundRequest(status)
    }
}
