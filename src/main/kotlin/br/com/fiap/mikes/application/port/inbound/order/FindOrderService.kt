package br.com.fiap.mikes.application.port.inbound.order

import br.com.fiap.mikes.application.core.domain.order.Order

interface FindOrderService {
    fun findOrdersWithDescriptions(): Result<List<Order>>
}
