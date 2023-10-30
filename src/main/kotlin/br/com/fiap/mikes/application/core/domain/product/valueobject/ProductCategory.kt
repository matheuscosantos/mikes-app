package br.com.fiap.mikes.application.core.domain.product.valueobject

import br.com.fiap.mikes.application.core.domain.exception.product.InvalidProductCategoryException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

enum class ProductCategory(val value: String) {
    SNACK("snack"),
    DRINK("drink"),
    DESSERT("dessert"),
    SIDE_DISH("side_dish"),
    ;

    companion object {
        fun new(value: String): Result<ProductCategory> = values().firstOrNull { it.value == value }
            ?.let { success(it) }
            ?: failure(InvalidProductCategoryException("$value is not a valid ProductCategory."))
    }
}
