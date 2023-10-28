package br.com.fiap.mikes.application.core.domain.exception.customer

import br.com.fiap.mikes.application.core.domain.exception.InvalidValueException

class InvalidCpfException(message: String) : InvalidValueException(TYPE, message) {

    companion object {
        private const val TYPE = "CPF"
    }
}
