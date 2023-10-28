package br.com.fiap.mikes.application.core.usecase.exception.product

import br.com.fiap.mikes.application.core.usecase.exception.InvalidDomainStateException

class InvalidProductStateException(message: String) : InvalidDomainStateException(TYPE, message) {

    companion object {
        private const val TYPE = "Product"
    }
}
