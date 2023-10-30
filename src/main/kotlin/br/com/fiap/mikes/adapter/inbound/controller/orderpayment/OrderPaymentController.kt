package br.com.fiap.mikes.adapter.inbound.controller.orderpayment

import br.com.fiap.mikes.adapter.inbound.controller.orderpayment.dto.OrderPaymentDto
import br.com.fiap.mikes.adapter.inbound.controller.orderpayment.dto.ProcessOrderPaymentRequest
import br.com.fiap.mikes.application.port.inbound.orderpayment.ProcessOrderPaymentService
import br.com.fiap.mikes.application.port.inbound.orderpayment.ConsultOrderPaymentStatusService
import br.com.fiap.mikes.application.port.inbound.orderpayment.dto.ConsultOrderPaymentStatusRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders-payment")
class OrderPaymentController(
    private val processOrderPaymentService: ProcessOrderPaymentService,
    private val consultOrderPaymentStatusService: ConsultOrderPaymentStatusService,
) {

    @GetMapping("/order/{orderId}")
    fun consult(@PathVariable orderId: String): ResponseEntity<OrderPaymentDto> {
        return consultOrderPaymentStatusService.execute(ConsultOrderPaymentStatusRequest(orderId))
            .map { OrderPaymentDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }

    @PostMapping("/webhook/process")
    fun process(@RequestBody processOrderPaymentRequest: ProcessOrderPaymentRequest): ResponseEntity<OrderPaymentDto> {
        return processOrderPaymentService.execute(processOrderPaymentRequest.toInbound())
            .map { OrderPaymentDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }
}
