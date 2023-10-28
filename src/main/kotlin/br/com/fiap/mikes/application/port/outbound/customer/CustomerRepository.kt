package br.com.fiap.mikes.application.port.outbound.customer

import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.port.outbound.customer.dto.CustomerOutboundResponse

interface CustomerRepository {
    fun save(customer: Customer): CustomerOutboundResponse
    fun find(cpf: Cpf, active: Boolean): CustomerOutboundResponse?
    fun exists(cpf: Cpf): Boolean
}
