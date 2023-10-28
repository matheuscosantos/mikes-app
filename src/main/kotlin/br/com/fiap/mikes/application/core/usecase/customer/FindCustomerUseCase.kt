package br.com.fiap.mikes.application.core.usecase.customer

import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.core.usecase.exception.customer.CustomerNotFoundException
import br.com.fiap.mikes.application.core.usecase.exception.customer.InvalidCustomerStateException
import br.com.fiap.mikes.application.mapper.customer.CustomerDomainMapper
import br.com.fiap.mikes.application.port.inbound.customer.FindCustomerService
import br.com.fiap.mikes.application.port.outbound.customer.CustomerRepository
import br.com.fiap.mikes.application.port.outbound.customer.dto.CustomerOutboundResponse
import br.com.fiap.mikes.util.mapFailure
import kotlin.Result.Companion.failure

class FindCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val customerDomainMapper: CustomerDomainMapper
) : FindCustomerService {

    override fun find(cpfValue: String, active: Boolean): Result<Customer> {
        val cpf = Cpf.new(cpfValue)
            .getOrElse { return failure(it) }

        val customerOutboundResponse = customerRepository.find(cpf, active)
            ?: return failure(CustomerNotFoundException("Cpf='${cpf.value}' not found for active='$active'."))

        return customerOutboundResponse
            .toCustomer()
            .mapFailure { (InvalidCustomerStateException("Customer in invalid state.")) }
    }

    private fun CustomerOutboundResponse.toCustomer(): Result<Customer> {
        return customerDomainMapper.new(this)
    }
}
