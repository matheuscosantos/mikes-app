package br.com.fiap.mikes.adapter.outbound.database.entity

import br.com.fiap.mikes.application.core.domain.orderpayment.OrderPayment
import br.com.fiap.mikes.application.port.outbound.orderpayment.dto.OrderPaymentOutboundResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "pagamento_pedido")
data class OrderPaymentEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String,

    @Column(name = "id_pedido", length = 36)
    val orderId: String,

    @Column(name = "status", length = 50)
    val status: String,

    @Column(name = "criado_em")
    val createdAt: LocalDateTime,

    @Column(name = "atualizado_em")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(orderPayment: OrderPayment): OrderPaymentEntity = with(orderPayment) {
            return OrderPaymentEntity(
                id = id.value,
                orderId = orderId.value,
                status = orderPaymentStatus.value,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }

    fun toOutbound(): OrderPaymentOutboundResponse {
        return OrderPaymentOutboundResponse(
            id,
            orderId,
            status,
            createdAt,
            updatedAt,
        )
    }
}
