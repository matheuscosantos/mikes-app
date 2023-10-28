package br.com.fiap.mikes.adapter.inbound.controller.order

import br.com.fiap.mikes.adapter.inbound.controller.order.dto.CreateOrderRequest
import br.com.fiap.mikes.adapter.inbound.controller.order.dto.OrderDto
import br.com.fiap.mikes.adapter.inbound.controller.order.dto.UpdateOrderStatusRequest
import br.com.fiap.mikes.application.port.inbound.order.CreateOrderService
import br.com.fiap.mikes.application.port.inbound.order.FindOrderService
import br.com.fiap.mikes.application.port.inbound.order.UpdateOrderStatusService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    private val createOrderService: CreateOrderService,
    private val findOrderService: FindOrderService,
    private val updateOrderStatusService: UpdateOrderStatusService,
) {

    @GetMapping
    fun findOrdersWithDescriptions(): ResponseEntity<List<OrderDto>> {
        return findOrderService.findOrdersWithDescriptions()
            .map { it.map { order -> OrderDto.from(order) } }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }

    @PostMapping
    fun create(@RequestBody createOrderRequest: CreateOrderRequest): ResponseEntity<OrderDto> {
        return createOrderService.create(createOrderRequest.toInbound())
            .map { OrderDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }

    @PutMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: String,
        @RequestBody updateOrderStatusRequest: UpdateOrderStatusRequest,
    ): ResponseEntity<OrderDto> {
        return updateOrderStatusService.update(id, updateOrderStatusRequest.toInbound())
            .map { OrderDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }
}
