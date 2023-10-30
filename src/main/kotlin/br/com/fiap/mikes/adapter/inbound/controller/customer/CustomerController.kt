package br.com.fiap.mikes.adapter.inbound.controller.customer

import br.com.fiap.mikes.adapter.inbound.controller.customer.dto.CreateCustomerRequest
import br.com.fiap.mikes.adapter.inbound.controller.customer.dto.CustomerDto
import br.com.fiap.mikes.application.port.inbound.customer.CreateCustomerService
import br.com.fiap.mikes.application.port.inbound.customer.FindCustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val createCustomerService: CreateCustomerService,
    private val findCustomerService: FindCustomerService,
) {

    @PostMapping
    fun create(@RequestBody createCustomerRequest: CreateCustomerRequest): ResponseEntity<CustomerDto> {
        return createCustomerService.create(createCustomerRequest.toInbound())
            .map { CustomerDto.from(it) }
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }
            .getOrThrow()
    }

    @GetMapping("/{cpfValue}")
    fun find(
        @PathVariable cpfValue: String,
        @RequestParam(required = false, defaultValue = "true") active: Boolean,
    ): ResponseEntity<CustomerDto> {
        return findCustomerService.find(cpfValue, active)
            .map { CustomerDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .getOrThrow()
    }
}
