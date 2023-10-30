package br.com.fiap.mikes.application.mapper.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductName
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductPrice
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductCategory
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductDescription
import br.com.fiap.mikes.application.port.inbound.product.dto.CreateProductInboundRequest
import br.com.fiap.mikes.application.port.inbound.product.dto.UpdateProductInboundRequest
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import java.time.LocalDateTime
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class DefaultProductDomainMapper: ProductDomainMapper {
    override fun new(
        createProductInboundRequest: CreateProductInboundRequest,
        id: ProductId,
        active: Boolean,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ): Result<Product> = with(createProductInboundRequest) {
        val name = ProductName.new(name).getOrElse { return failure(it) }
        val price = ProductPrice.new(price).getOrElse { return failure(it) }
        val category = ProductCategory.new(category).getOrElse { return failure(it) }
        val description = ProductDescription.new(description).getOrElse { return failure(it) }

        return Product.new(id, name, price, category, description, active, createdAt, updatedAt)
    }

    override fun new(
        productOutboundResponse: ProductOutboundResponse
    ): Result<Product> = with(productOutboundResponse) {
        val id = ProductId.new(id).getOrElse { return failure(it) }
        val name = ProductName.new(name).getOrElse { return failure(it) }
        val price = ProductPrice.new(price).getOrElse { return failure(it) }
        val category = ProductCategory.new(category).getOrElse { return failure(it) }
        val description = ProductDescription.new(description).getOrElse { return failure(it) }

        return Product.new(id, name, price, category, description, active, createdAt, updatedAt)
    }

    override fun update(
        updateProductInboundRequest: UpdateProductInboundRequest,
        product: Product
    ): Result<Product> = with(updateProductInboundRequest) {
        val name = ProductName.new(updateProductInboundRequest.name).getOrElse { return failure(it) }
        val price = ProductPrice.new(updateProductInboundRequest.price).getOrElse { return failure(it) }
        val category = ProductCategory.new(updateProductInboundRequest.category).getOrElse { return failure(it) }
        val description = ProductDescription.new(updateProductInboundRequest.description).getOrElse { return failure(it) }

        return success(product.updateValues(name, price, category, description))
    }
}
