package br.com.fiap.mikes.application.port.inbound.orderpayment

import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.port.inbound.orderpayment.dto.ConsultOrderPaymentStatusRequest

fun interface ConsultOrderPaymentStatusService {
    fun execute(consultOrderPaymentStatusRequest: ConsultOrderPaymentStatusRequest): Result<OrderPayment>
}
