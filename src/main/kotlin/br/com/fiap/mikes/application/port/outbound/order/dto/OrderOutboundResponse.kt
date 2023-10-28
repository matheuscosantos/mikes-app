package br.com.fiap.mikes.application.port.outbound.order.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderOutboundResponse(
    val idValue: String,
    val numberValue: Long,
    val cpfValue: String?,
    val items: List<OrderItemOutboundResponse>,
    val priceValue: BigDecimal,
    val orderStatusValue: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
