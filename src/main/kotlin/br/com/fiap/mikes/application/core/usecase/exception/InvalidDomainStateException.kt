package br.com.fiap.mikes.application.core.usecase.exception

open class InvalidDomainStateException(val type: String, message: String) : Exception(message)
