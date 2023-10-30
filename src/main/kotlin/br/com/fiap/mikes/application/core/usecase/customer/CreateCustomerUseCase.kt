package br.com.fiap.mikes.application.core.usecase.customer

import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.core.usecase.exception.customer.CustomerAlreadyExistsException
import br.com.fiap.mikes.application.core.usecase.exception.customer.InvalidCustomerStateException
import br.com.fiap.mikes.application.mapper.customer.CustomerDomainMapper
import br.com.fiap.mikes.application.port.inbound.customer.CreateCustomerService
import br.com.fiap.mikes.application.port.inbound.customer.dto.CreateCustomerInboundRequest
import br.com.fiap.mikes.application.port.outbound.customer.CustomerRepository
import br.com.fiap.mikes.application.port.outbound.customer.dto.CustomerOutboundResponse
import br.com.fiap.mikes.util.mapFailure
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class CreateCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val customerDomainMapper: CustomerDomainMapper
) : CreateCustomerService {

    override fun create(createCustomerInboundRequest: CreateCustomerInboundRequest): Result<Customer> {
        val customer = createCustomerInboundRequest
            .newCustomer()
            .getOrElse { return failure(it) }

        if (customerRepository.exists(customer.cpf)) {
            return failure(CustomerAlreadyExistsException("Cpf already exists: '${customer.cpf.value}'."))
        }

        return customerRepository.save(customer)
            .toCustomer()
            .mapFailure { (InvalidCustomerStateException("Customer in invalid state.")) }
    }

    private fun CreateCustomerInboundRequest.newCustomer(): Result<Customer> {
        val active = true
        val now = LocalDateTime.now()
        return customerDomainMapper.new(this, active, now, now)
    }

    private fun CustomerOutboundResponse.toCustomer(): Result<Customer> {
        return customerDomainMapper.new(this)
    }
}
