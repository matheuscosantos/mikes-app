package br.com.fiap.mikes.application.core.usecase.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.core.usecase.exception.product.InvalidProductStateException
import br.com.fiap.mikes.application.core.usecase.exception.product.ProductNotFoundException
import br.com.fiap.mikes.application.mapper.product.ProductDomainMapper
import br.com.fiap.mikes.application.port.inbound.product.UpdateProductService
import br.com.fiap.mikes.application.port.inbound.product.dto.UpdateProductInboundRequest
import br.com.fiap.mikes.application.port.outbound.product.ProductRepository
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import br.com.fiap.mikes.util.mapFailure
import kotlin.Result.Companion.failure

class UpdateProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper

) : UpdateProductService {

    override fun update(idValue: String, updateProductInboundRequest: UpdateProductInboundRequest): Result<Product> {
        val toUpdateProduct = findActiveProduct(idValue)
            .getOrElse { return failure(it) }

        val updatedProduct = productDomainMapper.update(updateProductInboundRequest, toUpdateProduct)
            .getOrElse { return failure(it) }

        return productRepository.save(updatedProduct)
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun findActiveProduct(idValue: String): Result<Product> {
        val productId = ProductId.new(idValue).getOrElse { return failure(it) }

        val productOutboundResponse = productRepository.find(productId, active = true)
            ?: return failure(ProductNotFoundException("Product not found."))

        return productOutboundResponse
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
