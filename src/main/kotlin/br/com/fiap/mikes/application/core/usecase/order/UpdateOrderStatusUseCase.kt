package br.com.fiap.mikes.application.core.usecase.order

import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderStatus
import br.com.fiap.mikes.application.core.usecase.exception.order.InvalidOrderStateException
import br.com.fiap.mikes.application.core.usecase.exception.order.OrderNotFoundException
import br.com.fiap.mikes.application.mapper.order.OrderDomainMapper
import br.com.fiap.mikes.application.port.inbound.order.UpdateOrderStatusService
import br.com.fiap.mikes.application.port.inbound.order.dto.UpdateOrderStatusInboundRequest
import br.com.fiap.mikes.application.port.outbound.order.OrderRepository
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderOutboundResponse
import br.com.fiap.mikes.util.flatMap
import br.com.fiap.mikes.util.mapFailure
import kotlin.Result.Companion.failure

class UpdateOrderStatusUseCase(
    private val orderRepository: OrderRepository,
    private val orderDomainMapper: OrderDomainMapper,
) : UpdateOrderStatusService {

    override fun update(id: String, updateOrderStatusInboundRequest: UpdateOrderStatusInboundRequest): Result<Order> {
        val newStatus = OrderStatus.findByValue(updateOrderStatusInboundRequest.status)
            .getOrElse { return failure(it) }

        val orderId = OrderId.new(id)
            .getOrElse { return failure(it) }

        return find(orderId)
            .flatMap { update(it, newStatus) }
    }

    private fun find(orderId: OrderId): Result<Order> {
        val orderOutboundResponse = orderRepository.findById(orderId)
            ?: return failure(OrderNotFoundException("OrderId='${orderId.value}' not found."))

        return orderOutboundResponse
            .toOrder()
            .mapFailure { InvalidOrderStateException("OrderId='${orderId.value}' in invalid state.") }
    }

    private fun update(order: Order, newStatus: OrderStatus): Result<Order> {
        val updatedOrder = order.updateStatus(newStatus)

        return orderRepository.save(updatedOrder)
            .toOrder()
            .mapFailure { InvalidOrderStateException("OrderId='${order.id}' in invalid state.") }
    }

    private fun OrderOutboundResponse.toOrder(): Result<Order> {
        return orderDomainMapper.new(this)
    }
}
