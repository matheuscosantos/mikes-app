package br.com.fiap.mikes.application.port.inbound.product.dto

import java.math.BigDecimal

data class UpdateProductInboundRequest(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: String,
)
