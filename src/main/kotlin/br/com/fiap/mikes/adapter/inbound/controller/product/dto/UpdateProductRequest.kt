package br.com.fiap.mikes.adapter.inbound.controller.product.dto

import br.com.fiap.mikes.application.port.inbound.product.dto.UpdateProductInboundRequest
import java.math.BigDecimal

data class UpdateProductRequest(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: String,
) {

    fun toInbound() = UpdateProductInboundRequest(name, description, price, category)
}
