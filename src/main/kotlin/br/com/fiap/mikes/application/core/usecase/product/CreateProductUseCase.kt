package br.com.fiap.mikes.application.core.usecase.product

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.core.usecase.exception.product.InvalidProductStateException
import br.com.fiap.mikes.application.mapper.product.ProductDomainMapper
import br.com.fiap.mikes.application.port.inbound.product.CreateProductService
import br.com.fiap.mikes.application.port.inbound.product.dto.CreateProductInboundRequest
import br.com.fiap.mikes.application.port.outbound.product.ProductRepository
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import br.com.fiap.mikes.util.mapFailure
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class CreateProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper
) : CreateProductService {

    override fun create(createProductInboundRequest: CreateProductInboundRequest): Result<Product> {
        val product = createProductInboundRequest
            .newProduct()
            .getOrElse { return failure(it) }

        return productRepository.save(product)
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun CreateProductInboundRequest.newProduct(): Result<Product> {
        val id = ProductId.generate()
        val active = true
        val now = LocalDateTime.now()
        return productDomainMapper.new(this, id, active, now, now)
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
