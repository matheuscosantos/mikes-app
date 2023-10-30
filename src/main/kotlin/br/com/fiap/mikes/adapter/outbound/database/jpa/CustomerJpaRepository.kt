package br.com.fiap.mikes.adapter.outbound.database.jpa

import br.com.fiap.mikes.adapter.outbound.database.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CustomerJpaRepository : JpaRepository<CustomerEntity, String> {
    fun findByCpfAndActive(cpf: String, active: Boolean): Optional<CustomerEntity>
}
