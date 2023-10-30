package br.com.fiap.mikes.infrastructure.instantiation

import br.com.fiap.mikes.adapter.outbound.database.OrderPaymentDatabaseRepository
import br.com.fiap.mikes.adapter.outbound.database.jpa.OrderPaymentJpaRepository
import br.com.fiap.mikes.application.core.usecase.orderpayment.ProcessOrderPaymentUseCase
import br.com.fiap.mikes.application.core.usecase.orderpayment.ConsultOrderPaymentStatusUseCase
import br.com.fiap.mikes.application.core.usecase.orderpayment.CreateOrderPaymentUseCase
import br.com.fiap.mikes.application.mapper.orderpayment.DefaultOrderPaymentDomainMapper
import br.com.fiap.mikes.application.mapper.orderpayment.OrderPaymentDomainMapper
import br.com.fiap.mikes.application.port.inbound.orderpayment.ConsultOrderPaymentStatusService
import br.com.fiap.mikes.application.port.outbound.orderpayment.OrderPaymentRepository
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderPaymentPortsInstantiationConfig {

    @Bean
    fun orderPaymentDomainMapper(): OrderPaymentDomainMapper {
        return DefaultOrderPaymentDomainMapper()
    }

    @Bean
    fun orderPaymentRepository(orderPaymentJpaRepository: OrderPaymentJpaRepository): OrderPaymentRepository {
        return (@Transactional object : OrderPaymentDatabaseRepository(orderPaymentJpaRepository) {})
    }

    @Bean
    fun consultOrderPaymentStatusService(
        orderPaymentRepository: OrderPaymentRepository,
        orderPaymentDomainMapper: OrderPaymentDomainMapper,
    ): ConsultOrderPaymentStatusService {
        return ConsultOrderPaymentStatusUseCase(orderPaymentRepository, orderPaymentDomainMapper)
    }

    @Bean
    fun createOrderPaymentService(
        orderPaymentRepository: OrderPaymentRepository,
        orderPaymentDomainMapper: OrderPaymentDomainMapper,
    ): CreateOrderPaymentUseCase {
        return CreateOrderPaymentUseCase(orderPaymentRepository, orderPaymentDomainMapper)
    }

    @Bean
    fun changeOrderPaymentStatusService(
        orderPaymentRepository: OrderPaymentRepository,
        orderPaymentDomainMapper: OrderPaymentDomainMapper,
        consultOrderPaymentStatusService: ConsultOrderPaymentStatusService,
    ): ProcessOrderPaymentUseCase {
        return ProcessOrderPaymentUseCase(orderPaymentRepository, orderPaymentDomainMapper, consultOrderPaymentStatusService)
    }
}
