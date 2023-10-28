package br.com.fiap.mikes.application.core.usecase.exception.customer

import br.com.fiap.mikes.application.core.usecase.exception.AlreadyExistsException

class CustomerAlreadyExistsException(message: String) : AlreadyExistsException(TYPE, message) {

    companion object {
        private const val TYPE = "Customer"
    }
}
