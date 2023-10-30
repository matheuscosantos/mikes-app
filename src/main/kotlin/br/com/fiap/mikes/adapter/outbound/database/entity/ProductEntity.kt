package br.com.fiap.mikes.adapter.outbound.database.entity

import br.com.fiap.mikes.application.core.domain.product.Product
import br.com.fiap.mikes.application.port.outbound.product.dto.ProductOutboundResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "produto")
data class ProductEntity(

    @Id
    @Column(name = "id", length = 36)
    val id: String,

    @Column(name = "nome", length = 255)
    val name: String,

    @Column(name = "preco")
    val price: BigDecimal,

    @Column(name = "categoria", length = 255)
    val category: String,

    @Column(name = "descricao", length = 255)
    val description: String,

    @Column(name = "ativo")
    val active: Boolean,

    @Column(name = "criado_em")
    val createdAt: LocalDateTime,

    @Column(name = "atualizado_em")
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun from(product: Product): ProductEntity = with(product) {
            return ProductEntity(
                id.value,
                name.value,
                price.value,
                category.value,
                description.value,
                active,
                createdAt,
                updatedAt,
            )
        }
    }

    fun toOutbound(): ProductOutboundResponse {
        return ProductOutboundResponse(
            id,
            name,
            price,
            category,
            description,
            active,
            createdAt,
            updatedAt,
        )
    }
}
