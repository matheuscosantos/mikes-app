package br.com.fiap.mikes.adapter.outbound.database.jpa

import br.com.fiap.mikes.adapter.outbound.database.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, String> {

    @Query(value = "SELECT nextval('numero_pedido')", nativeQuery = true)
    fun findNumber(): Long

    @Query(
        "SELECT p FROM pedido p " +
            "WHERE p.status != 'finished' " +
            "ORDER BY " +
            "CASE " +
            "WHEN p.status = 'ready' THEN 1 " +
            "WHEN p.status = 'preparing' THEN 2 " +
            "WHEN p.status = 'received' THEN 3 " +
            "ELSE 4 " +
            "END, " +
            "p.createdAt",
    )
    fun findOrdersWithDescriptions(): List<OrderEntity>
}
