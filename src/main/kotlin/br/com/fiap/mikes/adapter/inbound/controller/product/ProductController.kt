package br.com.fiap.mikes.adapter.inbound.controller.product

import br.com.fiap.mikes.adapter.inbound.controller.product.dto.CreateProductRequest
import br.com.fiap.mikes.adapter.inbound.controller.product.dto.ProductDto
import br.com.fiap.mikes.adapter.inbound.controller.product.dto.UpdateProductRequest
import br.com.fiap.mikes.application.port.inbound.product.CreateProductService
import br.com.fiap.mikes.application.port.inbound.product.DeleteProductService
import br.com.fiap.mikes.application.port.inbound.product.FindProductService
import br.com.fiap.mikes.application.port.inbound.product.UpdateProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val createProductService: CreateProductService,
    private val updateProductService: UpdateProductService,
    private val deleteProductService: DeleteProductService,
    private val findProductService: FindProductService,
) {

    @PostMapping
    fun create(@RequestBody createProductRequest: CreateProductRequest): ResponseEntity<ProductDto> =
        createProductService.create(createProductRequest.toInbound())
            .map { ResponseEntity.status(HttpStatus.CREATED).body(ProductDto.from(it)) }
            .getOrThrow()

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody updateProductRequest: UpdateProductRequest,
    ): ResponseEntity<ProductDto> =
        updateProductService.update(id, updateProductRequest.toInbound())
            .map { ResponseEntity.status(HttpStatus.OK).body(ProductDto.from(it)) }
            .getOrThrow()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> =
        deleteProductService.delete(id)
            .map { ResponseEntity.status(HttpStatus.NO_CONTENT).body(it) }
            .getOrThrow()

    @GetMapping("/category")
    fun findByCategory(
        @RequestParam("value") categoryValue: String,
        @RequestParam(required = false, defaultValue = "true") active: Boolean,
    ): ResponseEntity<List<ProductDto>> =
        findProductService.findAll(categoryValue, active)
            .map { it.map { product -> ProductDto.from(product) } }
            .map { ResponseEntity.status(HttpStatus.OK).body(it) }
            .getOrThrow()
}
