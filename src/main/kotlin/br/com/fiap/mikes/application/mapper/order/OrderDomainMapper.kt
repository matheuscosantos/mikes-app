package br.com.fiap.mikes.application.mapper.order

import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.core.domain.order.OrderItem
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemQuantity
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderNumber
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderStatus
import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderItemOutboundResponse
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderOutboundResponse
import java.time.LocalDateTime

interface OrderDomainMapper {
    fun new(
        id: OrderId,
        orderNumber: OrderNumber,
        nullableCpf: Cpf?,
        items: List<OrderItem>,
        orderStatus: OrderStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Order>

    fun new(id: OrderItemId, product: Product, quantity: OrderItemQuantity): Result<OrderItem>
    fun new(orderOutboundResponse: OrderOutboundResponse): Result<Order>
    fun new(orderItemOutboundResponse: OrderItemOutboundResponse): Result<OrderItem>
}
