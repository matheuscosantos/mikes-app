package br.com.fiap.mikes.application.mapper.customer

import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.core.domain.customer.valueobject.Email
import br.com.fiap.mikes.application.core.domain.customer.valueobject.PersonName
import br.com.fiap.mikes.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import br.com.fiap.mikes.application.port.outbound.customer.dto.CustomerOutboundResponse
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class DefaultCustomerDomainMapper: CustomerDomainMapper {

    override fun new(
        createCustomerInboundRequest: CreateCustomerInboundRequest,
        active: Boolean,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ): Result<Customer> = with(createCustomerInboundRequest) {
        val cpf = Cpf.new(cpf).getOrElse { return failure(it) }
        val name = PersonName.new(name).getOrElse { return failure(it) }
        val email = Email.new(email).getOrElse { return failure(it) }

        return Customer.new(cpf, name, email, active, createdAt, updatedAt)
    }

    override fun new(
        customerOutboundResponse: CustomerOutboundResponse
    ): Result<Customer> = with (customerOutboundResponse) {
        val cpf = Cpf.new(cpf).getOrElse { return failure(it) }
        val name = PersonName.new(name).getOrElse { return failure(it) }
        val email = Email.new(email).getOrElse { return failure(it) }

        return Customer.new(cpf, name, email, active, createdAt, updatedAt)
    }
}
