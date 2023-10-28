package br.com.fiap.mikes.application.core.usecase.exception.product

import br.com.fiap.mikes.application.core.usecase.exception.NotFoundException

class ProductNotFoundException(message: String) : NotFoundException(TYPE, message) {

    companion object {
        private const val TYPE = "Product"
    }
}
