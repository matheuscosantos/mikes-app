package br.com.fiap.mikes.infrastructure.instantiation

import br.com.fiap.mikes.adapter.outbound.database.ProductDatabaseRepository
import br.com.fiap.mikes.adapter.outbound.database.jpa.ProductJpaRepository
import br.com.fiap.mikes.application.core.usecase.product.CreateProductUseCase
import br.com.fiap.mikes.application.core.usecase.product.DeleteProductUseCase
import br.com.fiap.mikes.application.core.usecase.product.FindProductUseCase
import br.com.fiap.mikes.application.core.usecase.product.UpdateProductUseCase
import br.com.fiap.mikes.application.mapper.product.DefaultProductDomainMapper
import br.com.fiap.mikes.application.mapper.product.ProductDomainMapper
import br.com.fiap.mikes.application.port.outbound.product.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductPortsInstantiationConfig {

    @Bean
    fun productDomainMapper(): ProductDomainMapper {
        return DefaultProductDomainMapper()
    }

    @Bean
    fun productRepository(productJpaRepository: ProductJpaRepository): ProductRepository {
        return ProductDatabaseRepository(productJpaRepository)
    }

    @Bean
    fun createProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper
    ): CreateProductUseCase {
        return CreateProductUseCase(productRepository, productDomainMapper)
    }

    @Bean
    fun updateProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper
    ): UpdateProductUseCase {
        return UpdateProductUseCase(productRepository, productDomainMapper)
    }

    @Bean
    fun deleteProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper
    ): DeleteProductUseCase {
        return DeleteProductUseCase(productRepository, productDomainMapper)
    }

    @Bean
    fun findProductService(
        productRepository: ProductRepository,
        productDomainMapper: ProductDomainMapper
    ): FindProductUseCase {
        return FindProductUseCase(productRepository, productDomainMapper)
    }
}
