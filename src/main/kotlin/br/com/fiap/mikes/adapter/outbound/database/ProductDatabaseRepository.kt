package br.com.fiap.mikes.adapter.outbound.database

import br.com.fiap.mikes.adapter.outbound.database.entity.ProductEntity
import br.com.fiap.mikes.adapter.outbound.database.jpa.ProductJpaRepository
import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductCategory
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.port.outbound.product.ProductRepository
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import kotlin.jvm.optionals.getOrNull

class ProductDatabaseRepository(
    private val productJpaRepository: ProductJpaRepository
): ProductRepository {

    override fun save(product: Product): ProductOutboundResponse {
        return productJpaRepository.save(ProductEntity.from(product))
            .toOutbound()
    }

    override fun find(id: ProductId, active: Boolean): ProductOutboundResponse? {
        return productJpaRepository.findByIdAndActive(id.value, active)
            .map { it.toOutbound() }
            .getOrNull()
    }

    override fun findByCategory(productCategory: ProductCategory, active: Boolean): List<ProductOutboundResponse> {
        return productJpaRepository.findAllByCategoryAndActive(productCategory.value, active)
            .map { it.toOutbound() }
    }
}
