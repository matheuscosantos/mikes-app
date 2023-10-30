package br.com.fiap.mikes.application.port.inbound.order.dto

data class CreateOrderItemInboundRequest(
    val productId: String,
    val quantity: Long,
)
