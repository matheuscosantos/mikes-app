package br.com.fiap.mikes.application.core.domain.exception.order

import br.com.fiap.mikes.application.core.domain.exception.InvalidValueException

class InvalidOrderItemIdException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "OrderItemId"
    }
}