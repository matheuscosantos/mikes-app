package br.com.fiap.mikes.application.core.usecase.exception.orderpayment

import br.com.fiap.mikes.application.core.usecase.exception.InvalidDomainStateException

class InvalidOrderPaymentStateException(message: String) : InvalidDomainStateException(TYPE, message) {

    companion object {
        private const val TYPE = "OrderPayment"
    }
}
