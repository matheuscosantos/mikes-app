package br.com.fiap.mikes.application.core.usecase.order

import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderStatusException
import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.core.domain.order.OrderItem
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemQuantity
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderNumber
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderStatus
import br.com.fiap.mikes.application.mapper.order.OrderDomainMapper
import br.com.fiap.mikes.application.port.inbound.customer.FindCustomerService
import br.com.fiap.mikes.application.port.inbound.order.CreateOrderService
import br.com.fiap.mikes.application.port.inbound.order.dto.CreateOrderInboundRequest
import br.com.fiap.mikes.application.port.inbound.order.dto.CreateOrderItemInboundRequest
import br.com.fiap.mikes.application.port.inbound.orderpayment.CreateOrderPaymentService
import br.com.fiap.mikes.application.port.inbound.product.FindProductService
import br.com.fiap.mikes.application.port.outbound.order.OrderRepository
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderOutboundResponse
import br.com.fiap.mikes.util.flatMap
import br.com.fiap.mikes.util.mapFailure
import java.time.LocalDateTime
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val orderDomainMapper: OrderDomainMapper,
    private val findCustomerService: FindCustomerService,
    private val findProductService: FindProductService,
    private val createOrderPaymentService: CreateOrderPaymentService,
) : CreateOrderService {

    override fun create(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order> {
        return createOrder(createOrderInboundRequest)
            .flatMap { saveOrder(it) }
            .onSuccess { createOrderPaymentService.execute(it.id) }
    }

    private fun createOrder(createOrderInboundRequest: CreateOrderInboundRequest): Result<Order> {
        val nullableCpf = findNullableCpf(createOrderInboundRequest.cpf)
            .getOrElse { return failure(it) }

        val orderItems = createOrderItems(createOrderInboundRequest.items)
            .getOrElse { return failure(it) }

        val orderNumberValue = findOrderNumber()
            .getOrElse { return failure(it) }

        val now = LocalDateTime.now()

        return orderDomainMapper.new(
            OrderId.generate(),
            orderNumberValue,
            nullableCpf,
            orderItems,
            OrderStatus.RECEIVED,
            now,
            now,
        )
    }

    private fun findNullableCpf(nullableCpf: String?): Result<Cpf?> {
        val cpf = nullableCpf
            ?: return success(null)

        val customer = findCustomerService.find(cpf, active = true)
            .getOrElse { return failure(it) }

        return success(customer.cpf)
    }

    private fun createOrderItems(createOrderItemsInboundRequest: List<CreateOrderItemInboundRequest>): Result<List<OrderItem>> {
        val orderItems = createOrderItemsInboundRequest.map {
            createOrderItem(it).getOrElse { e -> return failure(e) }
        }

        return success(orderItems)
    }

    private fun createOrderItem(item: CreateOrderItemInboundRequest): Result<OrderItem> {
        val id = OrderItemId.generate()
        val product = findProductService.find(item.productId, active = true).getOrElse { return failure(it) }
        val quantity = OrderItemQuantity.new(item.quantity).getOrElse { return failure(it) }

        return orderDomainMapper.new(id, product, quantity)
    }

    private fun findOrderNumber(): Result<OrderNumber> {
        val orderNumberValue = orderRepository.findNumber()

        return OrderNumber.new(orderNumberValue)
    }

    private fun saveOrder(order: Order): Result<Order> {
        return orderRepository.save(order)
            .toOrder()
            .mapFailure { InvalidOrderStatusException("Order in invalid state.") }
    }

    private fun OrderOutboundResponse.toOrder(): Result<Order> {
        return orderDomainMapper.new(this)
    }
}
