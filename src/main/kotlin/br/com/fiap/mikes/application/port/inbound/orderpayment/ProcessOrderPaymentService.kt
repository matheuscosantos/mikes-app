package br.com.fiap.mikes.application.port.inbound.orderpayment

import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.port.inbound.orderpayment.dto.ProcessOrderPaymentInboundRequest

fun interface ProcessOrderPaymentService {
    fun execute(processOrderPaymentInboundRequest: ProcessOrderPaymentInboundRequest): Result<OrderPayment>
}
