package br.com.fiap.mikes.application.port.inbound.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.port.inbound.product.dto.UpdateProductInboundRequest

fun interface UpdateProductService {
    fun update(idValue: String, updateProductInboundRequest: UpdateProductInboundRequest): Result<Product>
}
