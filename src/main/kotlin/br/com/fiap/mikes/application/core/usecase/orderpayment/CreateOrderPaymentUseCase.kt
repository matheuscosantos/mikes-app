package br.com.fiap.mikes.application.core.usecase.orderpayment

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderStatusException
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentId
import br.com.fiap.mikes.application.core.domain.orderpayment.valueobject.OrderPaymentStatus
import br.com.fiap.mikes.application.mapper.orderpayment.OrderPaymentDomainMapper
import br.com.fiap.mikes.application.port.inbound.orderpayment.CreateOrderPaymentService
import br.com.fiap.mikes.application.port.outbound.orderpayment.OrderPaymentRepository
import br.com.fiap.mikes.application.port.outbound.orderpayment.dto.OrderPaymentOutboundResponse
import br.com.fiap.mikes.util.flatMap
import br.com.fiap.mikes.util.mapFailure
import java.time.LocalDateTime

class CreateOrderPaymentUseCase(
    private val orderPaymentRepository: OrderPaymentRepository,
    private val orderPaymentDomainMapper: OrderPaymentDomainMapper,
) : CreateOrderPaymentService {

    override fun execute(orderId: OrderId): Result<OrderPayment> {
        return createOrderPayment(orderId)
            .flatMap { saveOrderPayment(it) }
    }

    private fun createOrderPayment(orderId: OrderId): Result<OrderPayment> {
        return orderPaymentDomainMapper.new(
            OrderPaymentId.generate(),
            orderId,
            OrderPaymentStatus.WAITING,
            LocalDateTime.now(),
            LocalDateTime.now(),
        )
    }

    private fun saveOrderPayment(orderPayment: OrderPayment): Result<OrderPayment> {
        return orderPaymentRepository.save(orderPayment)
            .toOrderPayment()
            .mapFailure { InvalidOrderStatusException("OrderPayment in invalid state.") }
    }

    private fun OrderPaymentOutboundResponse.toOrderPayment(): Result<OrderPayment> {
        return orderPaymentDomainMapper.new(this)
    }
}
