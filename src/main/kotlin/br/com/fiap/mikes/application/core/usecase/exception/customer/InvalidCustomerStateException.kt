package br.com.fiap.mikes.application.core.usecase.exception.customer

import br.com.fiap.mikes.application.core.usecase.exception.InvalidDomainStateException

class InvalidCustomerStateException(message: String) : InvalidDomainStateException(TYPE, message) {

    companion object {
        private const val TYPE = "Customer"
    }
}
