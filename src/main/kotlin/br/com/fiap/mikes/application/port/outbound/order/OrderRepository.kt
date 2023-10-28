package br.com.fiap.mikes.application.port.outbound.order

import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderOutboundResponse

interface OrderRepository {
    fun findNumber(): Long
    fun save(order: Order): OrderOutboundResponse
    fun findOrdersWithDescriptions(): List<OrderOutboundResponse>
    fun findById(orderId: OrderId): OrderOutboundResponse?
}
