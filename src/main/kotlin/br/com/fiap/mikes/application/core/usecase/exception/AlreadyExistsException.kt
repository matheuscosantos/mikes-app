package br.com.fiap.mikes.application.core.usecase.exception

open class AlreadyExistsException(val type: String, message: String) : Exception(message)
