package br.com.fiap.mikes.application.port.inbound.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.port.inbound.product.dto.CreateProductInboundRequest

fun interface CreateProductService {
    fun create(createProductInboundRequest: CreateProductInboundRequest): Result<Product>
}
