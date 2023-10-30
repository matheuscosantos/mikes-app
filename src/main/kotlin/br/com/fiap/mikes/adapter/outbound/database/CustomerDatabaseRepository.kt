package br.com.fiap.mikes.adapter.outbound.database

import br.com.fiap.mikes.adapter.outbound.database.entity.CustomerEntity
import br.com.fiap.mikes.adapter.outbound.database.jpa.CustomerJpaRepository
import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.port.outbound.customer.CustomerRepository
import br.com.fiap.mikes.application.port.outbound.customer.dto.CustomerOutboundResponse
import kotlin.jvm.optionals.getOrNull

class CustomerDatabaseRepository(
    private val customerJpaRepository: CustomerJpaRepository,
) : CustomerRepository {

    override fun save(customer: Customer): CustomerOutboundResponse {
        return customerJpaRepository.save(CustomerEntity.from(customer))
            .toOutbound()
    }

    override fun find(cpf: Cpf, active: Boolean): CustomerOutboundResponse? {
        return customerJpaRepository.findByCpfAndActive(cpf.value, active)
            .map { it.toOutbound() }
            .getOrNull()
    }

    override fun exists(cpf: Cpf): Boolean {
        return customerJpaRepository.existsById(cpf.value)
    }
}
