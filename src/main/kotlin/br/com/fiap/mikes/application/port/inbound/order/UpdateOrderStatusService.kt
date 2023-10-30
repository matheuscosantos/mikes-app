package br.com.fiap.mikes.application.port.inbound.order

import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.port.inbound.order.dto.UpdateOrderStatusInboundRequest

fun interface UpdateOrderStatusService {
    fun update(id: String, updateOrderStatusInboundRequest: UpdateOrderStatusInboundRequest): Result<Order>
}
