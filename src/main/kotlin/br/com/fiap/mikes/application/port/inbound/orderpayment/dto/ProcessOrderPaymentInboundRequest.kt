package br.com.fiap.mikes.application.port.inbound.orderpayment.dto

data class ProcessOrderPaymentInboundRequest(
    val orderId: String,
    val paid: Boolean
)
