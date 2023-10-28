package br.com.fiap.mikes.application.core.usecase.exception

open class NotFoundException(val type: String, message: String) : Exception(message)
