package br.com.fiap.mikes.adapter.outbound.database.entity

import br.com.fiap.mikes.application.core.domain.customer.Customer
import br.com.fiap.mikes.application.port.outbound.customer.dto.CustomerOutboundResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "cliente")
data class CustomerEntity(

    @Id
    @Column(name = "cpf", length = 11)
    val cpf: String,

    @Column(name = "nome", length = 255)
    val name: String,

    @Column(name = "email", length = 255)
    val email: String,

    @Column(name = "ativo")
    val active: Boolean,

    @Column(name = "criado_em")
    val createdAt: LocalDateTime,

    @Column(name = "atualizado_em")
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun from(customer: Customer): CustomerEntity = with(customer) {
            return CustomerEntity(cpf.value, name.value, email.value, active, createdAt, updatedAt)
        }
    }

    fun toOutbound(): CustomerOutboundResponse {
        return CustomerOutboundResponse(
            cpf,
            name,
            email,
            active,
            createdAt,
            updatedAt,
        )
    }
}
