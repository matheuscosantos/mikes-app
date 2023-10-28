package br.com.fiap.mikes.adapter.outbound.database.jpa

import br.com.fiap.mikes.adapter.outbound.database.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProductJpaRepository : JpaRepository<ProductEntity, String> {
    fun findByIdAndActive(id: String, active: Boolean): Optional<ProductEntity>
    fun findAllByCategoryAndActive(category: String, active: Boolean): List<ProductEntity>
}