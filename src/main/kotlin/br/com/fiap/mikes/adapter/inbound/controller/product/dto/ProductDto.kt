package br.com.fiap.mikes.adapter.inbound.controller.product.dto

import br.com.fiap.mikes.application.core.domain.product.Product
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductDto(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val category: String,
    val description: String,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun from(product: Product): ProductDto = with(product) {
            return ProductDto(
                id.value,
                name.value,
                price.value,
                category.value,
                description.value,
                active,
                createdAt,
                updatedAt
            )
        }
    }
}
