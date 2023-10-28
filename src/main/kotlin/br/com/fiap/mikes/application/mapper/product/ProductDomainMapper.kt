package br.com.fiap.mikes.application.mapper.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.port.inbound.product.dto.CreateProductInboundRequest
import br.com.fiap.mikes.application.port.inbound.product.dto.UpdateProductInboundRequest
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import java.time.LocalDateTime

interface ProductDomainMapper {
    fun new(
        createProductInboundRequest: CreateProductInboundRequest,
        id: ProductId,
        active: Boolean,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ): Result<Product>

    fun new(productOutboundResponse: ProductOutboundResponse): Result<Product>

    fun update(updateProductInboundRequest: UpdateProductInboundRequest, product: Product): Result<Product>
}
