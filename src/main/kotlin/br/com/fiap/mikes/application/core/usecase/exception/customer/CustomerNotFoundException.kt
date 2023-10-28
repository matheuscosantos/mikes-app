package br.com.fiap.mikes.application.core.usecase.exception.customer

import br.com.fiap.mikes.application.core.usecase.exception.NotFoundException

class CustomerNotFoundException(message: String) : NotFoundException(TYPE, message) {

    companion object {
        private const val TYPE = "Customer"
    }
}
