package br.com.fiap.mikes.application.core.usecase.exception.order

import br.com.fiap.mikes.application.core.usecase.exception.NotFoundException

class OrderNotFoundException(message: String) : NotFoundException(TYPE, message) {

    companion object {
        private const val TYPE = "Order"
    }
}
