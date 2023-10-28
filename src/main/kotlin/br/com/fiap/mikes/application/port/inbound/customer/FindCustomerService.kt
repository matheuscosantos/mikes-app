package br.com.fiap.mikes.application.port.inbound.customer

import br.com.fiap.mikes.application.core.domain.customer.Customer

fun interface FindCustomerService {
    fun find(cpfValue: String, active: Boolean): Result<Customer>
}
