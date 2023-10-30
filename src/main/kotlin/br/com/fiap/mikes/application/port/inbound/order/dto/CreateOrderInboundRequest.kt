package br.com.fiap.mikes.application.port.inbound.order.dto

data class CreateOrderInboundRequest(
    val cpf: String?,
    val items: List<CreateOrderItemInboundRequest>
)
