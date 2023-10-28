package br.com.fiap.mikes.adapter.inbound.controller.customer.dto

import br.com.fiap.mikes.application.port.inbound.customer.dto.CreateCustomerInboundRequest

data class CreateCustomerRequest(
    val cpf: String,
    val name: String,
    val email: String,
 ) {

    fun toInbound(): CreateCustomerInboundRequest {
        return CreateCustomerInboundRequest(
            cpf,
            name,
            email
        )
    }
}
