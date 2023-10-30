package br.com.fiap.mikes.application.core.usecase.exception.order

import br.com.fiap.mikes.application.core.usecase.exception.AlreadyExistsException

class OrderAlreadyExistsException(message: String) : AlreadyExistsException(TYPE, message) {

    companion object {
        private const val TYPE = "Order"
    }
}
