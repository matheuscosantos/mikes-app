package br.com.fiap.mikes.application.core.usecase.exception.orderpayment

import br.com.fiap.mikes.application.core.usecase.exception.NotFoundException

class OrderPaymentNotFoundException(message: String) : NotFoundException(TYPE, message) {

    companion object {
        private const val TYPE = "OrderPayment"
    }
}
