package br.com.fiap.mikes.application.core.usecase.order

import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.mapper.order.OrderDomainMapper
import br.com.fiap.mikes.application.port.inbound.order.FindOrderService
import br.com.fiap.mikes.application.port.outbound.order.OrderRepository
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderOutboundResponse
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class FindOrderUseCase(
    private val orderRepository: OrderRepository,
    private val orderDomainMapper: OrderDomainMapper,
) : FindOrderService {

    override fun findOrdersWithDescriptions(): Result<List<Order>> {
        val orders = orderRepository.findOrdersWithDescriptions().map { it.toOrder().getOrElse { e -> return failure(e) } }
        return success(orders)
    }

    private fun OrderOutboundResponse.toOrder(): Result<Order> {
        return orderDomainMapper.new(this)
    }
}
