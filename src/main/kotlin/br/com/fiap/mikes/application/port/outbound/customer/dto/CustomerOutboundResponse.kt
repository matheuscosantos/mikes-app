package br.com.fiap.mikes.application.port.outbound.customer.dto

import java.time.LocalDateTime

data class CustomerOutboundResponse(
    val cpf: String,
    val name: String,
    val email: String,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
