package br.com.fiap.mikes.application.port.inbound.orderpayment

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment

fun interface CreateOrderPaymentService {
    fun execute(orderId: OrderId): Result<OrderPayment>
}
