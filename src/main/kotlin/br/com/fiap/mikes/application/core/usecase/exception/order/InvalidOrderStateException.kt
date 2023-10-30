package br.com.fiap.mikes.application.core.usecase.exception.order

import br.com.fiap.mikes.application.core.usecase.exception.InvalidDomainStateException

class InvalidOrderStateException(message: String) : InvalidDomainStateException(TYPE, message) {

    companion object {
        private const val TYPE = "Order"
    }
}
