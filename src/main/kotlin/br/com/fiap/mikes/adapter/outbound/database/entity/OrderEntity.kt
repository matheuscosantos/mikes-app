package br.com.fiap.mikes.adapter.outbound.database.entity

import br.com.fiap.mikes.application.core.domain.order.Order
import br.com.fiap.mikes.application.port.outbound.order.dto.OrderOutboundResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "pedido")
data class OrderEntity(

    @Id
    @Column(name = "id", length = 36)
    val id: String,

    @Column(name = "numero")
    val number: Long,

    @Column(name = "cpf", length = 11)
    val cpf: String?,

    @Column(name = "preco")
    val price: BigDecimal,

    @Column(name = "status", length = 50)
    val status: String,

    @Column(name = "criado_em")
    val createdAt: LocalDateTime,

    @Column(name = "atualizado_em")
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun from(order: Order): OrderEntity = with(order) {
            return OrderEntity(
                id.value,
                number.value,
                cpf?.value,
                price.value,
                orderStatus.value,
                createdAt,
                updatedAt,
            )
        }
    }

    fun toOutbound(orderItemsEntity: List<OrderItemEntity>): OrderOutboundResponse {
        val items = orderItemsEntity.map { it.toOutbound() }
        return OrderOutboundResponse(
            id,
            number,
            cpf,
            items,
            price,
            status,
            createdAt,
            updatedAt,
        )
    }
}
