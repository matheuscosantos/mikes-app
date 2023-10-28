package br.com.fiap.mikes.application.mapper.order

import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.core.domain.order.OrderItem
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderItemQuantity
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderNumber
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderStatus
import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductName
import br.com.fiap.mikes.application.core.domain.product.valueobject.ProductPrice
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderItemOutboundResponse
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderOutboundResponse
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class DefaultOrderDomainMapper : OrderDomainMapper {
    override fun new(
        id: OrderId,
        orderNumber: OrderNumber,
        nullableCpf: Cpf?,
        items: List<OrderItem>,
        orderStatus: OrderStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Order> {
        return Order.new(
            id,
            orderNumber,
            nullableCpf,
            items,
            orderStatus,
            LocalDateTime.now(),
            LocalDateTime.now(),
        )
    }

    override fun new(id: OrderItemId, product: Product, quantity: OrderItemQuantity): Result<OrderItem> {
        return OrderItem.new(
            id,
            product.name,
            product.price,
            quantity,
        )
    }

    override fun new(orderOutboundResponse: OrderOutboundResponse): Result<Order> = with(orderOutboundResponse) {
        val id = OrderId.new(idValue).getOrElse { return failure(it) }
        val number = OrderNumber.new(numberValue).getOrElse { return failure(it) }
        val cpf = cpfValue?.let { Cpf.new(cpfValue).getOrElse { return failure(it) } }
        val items = items.map { new(it).getOrElse { e -> return failure(e) } }
        val status = OrderStatus.findByValue(orderStatusValue).getOrElse { e -> return failure(e) }

        return Order.new(
            id,
            number,
            cpf,
            items,
            status,
            createdAt,
            updatedAt,
        )
    }

    override fun new(
        orderItemOutboundResponse: OrderItemOutboundResponse,
    ): Result<OrderItem> = with(orderItemOutboundResponse) {
        val id = OrderItemId.new(idValue).getOrElse { return failure(it) }
        val productName = ProductName.new(productNameValue).getOrElse { return failure(it) }
        val productPrice = ProductPrice.new(productPriceValue).getOrElse { return failure(it) }
        val quantity = OrderItemQuantity.new(quantityValue).getOrElse { return failure(it) }

        return OrderItem.new(id, productName, productPrice, quantity)
    }
}
