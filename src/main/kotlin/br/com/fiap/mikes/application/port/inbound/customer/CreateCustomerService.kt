package br.com.fiap.mikes.application.port.inbound.customer

import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.port.inbound.customer.dto.CreateCustomerInboundRequest

fun interface CreateCustomerService {
    fun create(createCustomerInboundRequest: CreateCustomerInboundRequest): Result<Customer>
}
