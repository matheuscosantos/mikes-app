package br.com.fiap.mikes.application.port.outbound.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductCategory
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse

interface ProductRepository {
    fun save(product: Product): ProductOutboundResponse
    fun find(id: ProductId, active: Boolean): ProductOutboundResponse?
    fun findByCategory(productCategory: ProductCategory, active: Boolean): List<ProductOutboundResponse>
}
