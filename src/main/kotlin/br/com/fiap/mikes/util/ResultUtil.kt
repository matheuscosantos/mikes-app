package br.com.fiap.mikes.util

fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return fold(
        { transform(it) },
        { Result.failure(it) },
    )
}

fun <T> Result<T>.mapFailure(transform: (e: Throwable) -> Throwable): Result<T> {
    return fold(
        { Result.success(it) },
        { Result.failure(transform(it)) },
    )
}
