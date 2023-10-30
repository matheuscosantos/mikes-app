package br.com.fiap.mikes.application.mapper.orderpayment

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentId
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentStatus
import br.com.fiap.mikes.application.port.outbound.orderpayment.dto.OrderPaymentOutboundResponse
import java.time.LocalDateTime

class DefaultOrderPaymentDomainMapper : OrderPaymentDomainMapper {

    override fun new(
        id: OrderPaymentId,
        orderId: OrderId,
        orderPaymentStatus: OrderPaymentStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<OrderPayment> {
        return OrderPayment.new(
            id,
            orderId,
            orderPaymentStatus,
            LocalDateTime.now(),
            LocalDateTime.now(),
        )
    }

    override fun new(orderPaymentOutboundResponse: OrderPaymentOutboundResponse): Result<OrderPayment> =
        with(orderPaymentOutboundResponse) {
            val id = OrderPaymentId.new(id).getOrElse { return Result.failure(it) }
            val orderId = OrderId.new(orderId).getOrElse { return Result.failure(it) }
            val status = OrderPaymentStatus.findByValue(orderPaymentStatus).getOrElse { return Result.failure(it) }

            return OrderPayment.new(
                id,
                orderId,
                status,
                createdAt,
                updatedAt,
            )
        }
}
