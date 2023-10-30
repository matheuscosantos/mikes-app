package br.com.fiap.mikes.application.core.domain.exception.product

import br.com.fiap.mikes.application.core.domain.exception.InvalidValueException

class InvalidProductNameException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "ProductName"
    }
}
