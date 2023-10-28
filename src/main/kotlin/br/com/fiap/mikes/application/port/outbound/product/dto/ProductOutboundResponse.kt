package br.com.fiap.mikes.application.port.outbound.product.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductOutboundResponse(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val category: String,
    val description: String,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)