package br.com.fiap.mikes.application.core.domain.customer

import br.com.fiap.mikes.application.core.domain.customer.valueobject.Cpf
import br.com.fiap.mikes.application.core.domain.customer.valueobject.Email
import br.com.fiap.mikes.application.core.domain.customer.valueobject.PersonName
import java.time.LocalDateTime
import kotlin.Result.Companion.success

class Customer private constructor(
    val cpf: Cpf,
    val name: PersonName,
    val email: Email,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun new(
            cpf: Cpf,
            personName: PersonName,
            email: Email,
            active: Boolean,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime
        ): Result<Customer> = success(Customer(cpf, personName, email, active, createdAt, updatedAt))
    }
}
