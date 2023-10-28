package br.com.fiap.mikes.application.core.usecase.orderpayment

import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.core.usecase.exception.order.OrderNotFoundException
import br.com.fiap.mikes.application.core.usecase.exception.orderpayment.InvalidOrderPaymentStateException
import br.com.fiap.mikes.application.mapper.orderpayment.OrderPaymentDomainMapper
import br.com.fiap.mikes.application.port.inbound.orderpayment.ConsultOrderPaymentStatusService
import br.com.fiap.mikes.application.port.inbound.orderpayment.dto.ConsultOrderPaymentStatusRequest
import br.com.fiap.mikes.application.port.outbound.orderpayment.OrderPaymentRepository
import br.com.fiap.mikes.util.mapFailure

class ConsultOrderPaymentStatusUseCase(
    private val orderPaymentRepository: OrderPaymentRepository,
    private val orderPaymentDomainMapper: OrderPaymentDomainMapper,
) : ConsultOrderPaymentStatusService {

    override fun execute(consultOrderPaymentStatusRequest: ConsultOrderPaymentStatusRequest): Result<OrderPayment> {
        val orderId = OrderId.new(consultOrderPaymentStatusRequest.orderId)
            .getOrElse { return Result.failure(it) }

        val orderPaymentOutboundResponse = orderPaymentRepository.findByOrderId(orderId)
            ?: return Result.failure(OrderNotFoundException("OrderPayment for OrderId='${orderId.value}' not found."))

        return orderPaymentDomainMapper.new(orderPaymentOutboundResponse)
            .mapFailure { InvalidOrderPaymentStateException("OrderPayment in invalid state.") }
    }
}
