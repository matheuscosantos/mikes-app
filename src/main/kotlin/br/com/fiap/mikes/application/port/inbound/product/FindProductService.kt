package br.com.fiap.mikes.application.port.inbound.product

import br.com.fiap.mikes.application.core.domain.product.Product

interface FindProductService {
    fun findAll(productCategoryValue: String, active: Boolean): Result<List<Product>>
    fun find(idValue: String, active: Boolean): Result<Product>
}
