package br.com.fiap.mikes.application.port.inbound.order

import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.port.inbound.order.dto.CreateOrderInboundRequest

fun interface CreateOrderService {
    fun create(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order>
}
