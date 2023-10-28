package br.com.fiap.mikes.application.core.usecase.orderpayment

import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.core.usecase.exception.orderpayment.InvalidOrderPaymentStateException
import br.com.fiap.mikes.application.mapper.orderpayment.OrderPaymentDomainMapper
import br.com.fiap.mikes.application.port.inbound.orderpayment.ConsultOrderPaymentStatusService
import br.com.fiap.mikes.application.port.inbound.orderpayment.ProcessOrderPaymentService
import br.com.fiap.mikes.application.port.inbound.orderpayment.dto.ConsultOrderPaymentStatusRequest
import br.com.fiap.mikes.application.port.inbound.orderpayment.dto.ProcessOrderPaymentInboundRequest
import br.com.fiap.mikes.application.port.outbound.orderpayment.OrderPaymentRepository
import br.com.fiap.mikes.util.mapFailure
import kotlin.Result.Companion.failure

class ProcessOrderPaymentUseCase(
    private val orderPaymentRepository: OrderPaymentRepository,
    private val orderPaymentDomainMapper: OrderPaymentDomainMapper,
    private val consultOrderPaymentStatusService: ConsultOrderPaymentStatusService
) : ProcessOrderPaymentService {

    override fun execute(processOrderPaymentInboundRequest: ProcessOrderPaymentInboundRequest): Result<OrderPayment> {
        val orderPayment = consultOrderPaymentStatusService.execute(ConsultOrderPaymentStatusRequest(processOrderPaymentInboundRequest.orderId))
            .getOrElse { return failure(it) }

        val updatedOrderPayment = orderPayment.payment(processOrderPaymentInboundRequest.paid)

        val orderPaymentOutboundResponse = orderPaymentRepository.save(updatedOrderPayment)

        return orderPaymentDomainMapper.new(orderPaymentOutboundResponse)
            .mapFailure { InvalidOrderPaymentStateException("OrderPayment in invalid state.") }
    }
}
