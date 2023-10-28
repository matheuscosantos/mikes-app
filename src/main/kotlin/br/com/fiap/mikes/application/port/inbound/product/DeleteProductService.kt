package br.com.fiap.mikes.application.port.inbound.product

fun interface DeleteProductService {
    fun delete(idValue: String): Result<Unit>
}
