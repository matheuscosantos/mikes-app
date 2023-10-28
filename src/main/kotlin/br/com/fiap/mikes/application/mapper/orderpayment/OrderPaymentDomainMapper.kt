package br.com.fiap.mikes.application.mapper.orderpayment

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentId
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentStatus
import br.com.fiap.mikes.application.port.outbound.orderpayment.dto.OrderPaymentOutboundResponse
import java.time.LocalDateTime

interface OrderPaymentDomainMapper {

    fun new(
        id: OrderPaymentId,
        orderId: OrderId,
        orderPaymentStatus: OrderPaymentStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<OrderPayment>

    fun new(orderPaymentOutboundResponse: OrderPaymentOutboundResponse): Result<OrderPayment>
}
