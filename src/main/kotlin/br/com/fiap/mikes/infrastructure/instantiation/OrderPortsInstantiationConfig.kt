package br.com.fiap.mikes.infrastructure.instantiation

import br.com.fiap.mikes.adapter.outbound.database.OrderDatabaseRepository
import br.com.fiap.mikes.adapter.outbound.database.jpa.OrderItemJpaRepository
import br.com.fiap.mikes.adapter.outbound.database.jpa.OrderJpaRepository
import br.com.fiap.mikes.application.core.usecase.order.CreateOrderUseCase
import br.com.fiap.mikes.application.core.usecase.order.FindOrderUseCase
import br.com.fiap.mikes.application.core.usecase.order.UpdateOrderStatusUseCase
import br.com.fiap.mikes.application.mapper.order.DefaultOrderDomainMapper
import br.com.fiap.mikes.application.mapper.order.OrderDomainMapper
import br.com.fiap.mikes.application.port.inbound.customer.FindCustomerService
import br.com.fiap.mikes.application.port.inbound.order.CreateOrderService
import br.com.fiap.mikes.application.port.inbound.order.FindOrderService
import br.com.fiap.mikes.application.port.inbound.orderpayment.CreateOrderPaymentService
import br.com.fiap.mikes.application.port.inbound.order.UpdateOrderStatusService
import br.com.fiap.mikes.application.port.inbound.product.FindProductService
import br.com.fiap.mikes.application.port.outbound.order.OrderRepository
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderPortsInstantiationConfig {

    @Bean
    fun orderDomainMapper(): OrderDomainMapper {
        return DefaultOrderDomainMapper()
    }

    @Bean
    fun orderRepository(
        orderJpaRepository: OrderJpaRepository,
        orderItemJpaRepository: OrderItemJpaRepository,
    ): OrderRepository {
        return (
            @Transactional object : OrderDatabaseRepository(
                orderJpaRepository,
                orderItemJpaRepository,
            ) {}
            )
    }

    @Bean
    fun createOrderService(
        orderRepository: OrderRepository,
        orderDomainMapper: OrderDomainMapper,
        findCustomerService: FindCustomerService,
        findProductService: FindProductService,
        createOrderPaymentService: CreateOrderPaymentService,
    ): CreateOrderService {
        return CreateOrderUseCase(
            orderRepository,
            orderDomainMapper,
            findCustomerService,
            findProductService,
            createOrderPaymentService,
        )
    }

    @Bean
    fun findOrderService(
        orderRepository: OrderRepository,
        orderDomainMapper: OrderDomainMapper,
    ): FindOrderService {
        return FindOrderUseCase(orderRepository, orderDomainMapper)
    }

    @Bean
    fun updateOrderStatusService(
        orderRepository: OrderRepository,
        orderDomainMapper: OrderDomainMapper,
    ): UpdateOrderStatusService {
        return UpdateOrderStatusUseCase(orderRepository, orderDomainMapper)
    }
}
