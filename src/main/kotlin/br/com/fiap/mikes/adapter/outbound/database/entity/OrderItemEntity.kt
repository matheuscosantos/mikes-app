package br.com.fiap.mikes.adapter.outbound.database.entity

import br.com.fiap.mikes.application.core.domain.order.OrderItem
import br.com.fiap.mikes.application.core.domain.order.valueobject.OrderId
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderItemOutboundResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity(name = "item_pedido")
data class OrderItemEntity(

    @Id
    @Column(name = "id", length = 36)
    val id: String,

    @Column(name = "id_pedido", length = 36)
    val orderId: String,

    @Column(name = "nome_produto", length = 255)
    val productName: String,

    @Column(name = "preco_produto")
    val productPrice: BigDecimal,

    @Column(name = "quantidade")
    val quantity: Long,

    @Column(name = "preco")
    val price: BigDecimal,
) {

    companion object {
        fun from(orderId: OrderId, orderItem: OrderItem): OrderItemEntity = with(orderItem) {
            return OrderItemEntity(
                id.value,
                orderId.value,
                productName.value,
                productPrice.value,
                quantity.value,
                price.value,
            )
        }
    }

    fun toOutbound(): OrderItemOutboundResponse {
        return OrderItemOutboundResponse(
            id,
            productName,
            productPrice,
            quantity,
            price,
        )
    }
}
