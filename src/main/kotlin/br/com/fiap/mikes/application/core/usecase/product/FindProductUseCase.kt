package br.com.fiap.mikes.application.core.usecase.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductCategory
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.core.usecase.exception.product.InvalidProductStateException
import br.com.fiap.mikes.application.core.usecase.exception.product.ProductNotFoundException
import br.com.fiap.mikes.application.mapper.product.ProductDomainMapper
import br.com.fiap.mikes.application.port.inbound.product.FindProductService
import br.com.fiap.mikes.application.port.outbound.product.ProductRepository
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import br.com.fiap.mikes.util.mapFailure
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class FindProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper
) : FindProductService {

    override fun findAll(productCategoryValue: String, active: Boolean): Result<List<Product>> {
        val productCategory = ProductCategory.new(productCategoryValue)
            .getOrElse { return failure(it) }

        val products = productRepository.findByCategory(productCategory, active)
            .map { it.toProduct().getOrElse { return failure(InvalidProductStateException("Product in invalid state.")) } }

        return success(products)
    }

    override fun find(idValue: String, active: Boolean): Result<Product> {
        val productId = ProductId.new(idValue)
            .getOrElse { return failure(it) }

        val productOutboundResponse = productRepository.find(productId, active)
            ?: return failure(ProductNotFoundException("Product not found."))

        return productOutboundResponse
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
