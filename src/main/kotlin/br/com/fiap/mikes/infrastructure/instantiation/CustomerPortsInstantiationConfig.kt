package br.com.fiap.mikes.infrastructure.instantiation

import br.com.fiap.mikes.adapter.outbound.database.CustomerDatabaseRepository
import br.com.fiap.mikes.adapter.outbound.database.jpa.CustomerJpaRepository
import br.com.fiap.mikes.application.core.usecase.customer.CreateCustomerUseCase
import br.com.fiap.mikes.application.core.usecase.customer.FindCustomerUseCase
import br.com.fiap.mikes.application.mapper.customer.CustomerDomainMapper
import br.com.fiap.mikes.application.mapper.customer.DefaultCustomerDomainMapper
import br.com.fiap.mikes.application.port.inbound.customer.CreateCustomerService
import br.com.fiap.mikes.application.port.inbound.customer.FindCustomerService
import br.com.fiap.mikes.application.port.outbound.customer.CustomerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomerPortsInstantiationConfig {

    @Bean
    fun customerDomainMapper(): CustomerDomainMapper {
        return DefaultCustomerDomainMapper()
    }

    @Bean
    fun customerRepository(
        customerJpaRepository: CustomerJpaRepository,
        customerDomainMapper: CustomerDomainMapper
    ): CustomerRepository {
        return CustomerDatabaseRepository(customerJpaRepository)
    }

    @Bean
    fun createCustomerService(
        customerRepository: CustomerRepository,
        customerDomainMapper: CustomerDomainMapper
    ): CreateCustomerService {
        return CreateCustomerUseCase(customerRepository, customerDomainMapper)
    }

    @Bean
    fun findCustomerService(
        customerRepository: CustomerRepository,
        customerDomainMapper: CustomerDomainMapper
    ): FindCustomerService {
        return FindCustomerUseCase(customerRepository, customerDomainMapper)
    }
}
