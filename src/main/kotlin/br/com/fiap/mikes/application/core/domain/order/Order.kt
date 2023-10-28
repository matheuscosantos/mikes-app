package br.com.fiap.mikes.application.core.domain.order

import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderNumber
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderPrice
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderStatus
import java.time.LocalDateTime
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class Order private constructor(
    val id: OrderId,
    val number: OrderNumber,
    val cpf: Cpf?,
    val items: List<OrderItem>,
    val price: OrderPrice,
    val orderStatus: OrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun new(
            id: OrderId,
            number: OrderNumber,
            cpf: Cpf?,
            items: List<OrderItem>,
            orderStatus: OrderStatus,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
        ): Result<Order> {
            val orderPrice = calculatePrice(items).getOrElse { return failure(it) }

            return success(Order(id, number, cpf, items, orderPrice, orderStatus, createdAt, updatedAt))
        }

        private fun calculatePrice(orderItems: List<OrderItem>): Result<OrderPrice> {
            val orderPriceValue = orderItems.sumOf { it.price.value }
            return OrderPrice.new(orderPriceValue)
        }
    }

    fun updateStatus(orderStatus: OrderStatus): Order {
        val updatedAt = LocalDateTime.now()

        return Order(
            id,
            number,
            cpf,
            items,
            price,
            orderStatus,
            createdAt,
            updatedAt
        )
    }
}
