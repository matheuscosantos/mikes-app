package br.com.fiap.mikes.application.core.usecase.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.core.usecase.exception.product.InvalidProductStateException
import br.com.fiap.mikes.application.core.usecase.exception.product.ProductNotFoundException
import br.com.fiap.mikes.application.mapper.product.ProductDomainMapper
import br.com.fiap.mikes.application.port.inbound.product.DeleteProductService
import br.com.fiap.mikes.application.port.outbound.product.ProductRepository
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class DeleteProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper
) : DeleteProductService {

    override fun delete(idValue: String): Result<Unit> {
        val productId = ProductId.new(idValue)
            .getOrElse { return failure(it) }

        val productOutboundResponse = productRepository.find(productId, active = true)
            ?: return failure(ProductNotFoundException("Product not found."))

        val product = productOutboundResponse
            .toProduct()
            .map { it.toInactive() }
            .getOrElse { return failure(InvalidProductStateException("Product in invalid state.")) }

        productRepository.save(product)

        return success(Unit)
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
