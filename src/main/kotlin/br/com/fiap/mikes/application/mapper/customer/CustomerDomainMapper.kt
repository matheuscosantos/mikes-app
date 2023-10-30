package br.com.fiap.mikes.application.mapper.customer

import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import br.com.fiap.mikes.application.port.outbound.customer.dto.CustomerOutboundResponse
import java.time.LocalDateTime

interface CustomerDomainMapper {
    fun new(
        createCustomerInboundRequest: CreateCustomerInboundRequest,
        active: Boolean,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Customer>

    fun new(customerOutboundResponse: CustomerOutboundResponse): Result<Customer>
}
