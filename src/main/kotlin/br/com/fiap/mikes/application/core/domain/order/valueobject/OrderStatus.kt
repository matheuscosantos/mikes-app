package br.com.fiap.mikes.application.core.domain.order.valueobject

import br.com.fiap.mikes.application.core.domain.exception.order.InvalidOrderStatusException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

enum class OrderStatus(val value: String) {
    RECEIVED("received"),
    PREPARING("preparing"),
    READY("ready"),
    FINISHED("finished"),
    ;

    companion object {
        fun findByValue(value: String): Result<OrderStatus> {
            val orderStatus = values().firstOrNull { it.value == value }
                ?: return failure(InvalidOrderStatusException("Invalid Order Status: '$value'."))

            return success(orderStatus)
        }
    }
}
