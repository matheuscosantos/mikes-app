package br.com.fiap.mikes.application.core.domain.product

import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductId
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductName
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductPrice
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductCategory
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductDescription
import java.time.LocalDateTime
import kotlin.Result.Companion.success

class Product private constructor(
    val id: ProductId,
    val name: ProductName,
    val price: ProductPrice,
    val category: ProductCategory,
    val description: ProductDescription,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun new(
            id: ProductId,
            name: ProductName,
            price: ProductPrice,
            category: ProductCategory,
            description: ProductDescription,
            active: Boolean,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime
        ): Result<Product> = success(Product(id, name, price, category, description, active, createdAt, updatedAt))
    }

    fun toInactive(): Product {
        val active = false
        val updatedAt = LocalDateTime.now()
        return Product(id, name, price, category, description, active, createdAt, updatedAt)
    }

    fun updateValues(
        name: ProductName,
        price: ProductPrice,
        category: ProductCategory,
        description: ProductDescription,
    ): Product {
        val updatedAt = LocalDateTime.now()
        return Product(id, name, price, category, description, active, createdAt, updatedAt)
    }
}
