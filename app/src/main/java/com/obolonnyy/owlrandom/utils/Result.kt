package com.obolonnyy.owlrandom.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

sealed class MyResult<out T> private constructor(private val value: Any?) {
    val isSuccess: Boolean get() = this is Success

    fun exceptionOrNull(): Throwable? =
        when (value) {
            is Error -> value.err
            else -> null
        }

    data class Success<T>(val data: T) : MyResult<T>(data)
    data class Error(val err: Throwable) : MyResult<Nothing>(err)
}

suspend fun <T> asResult(foo: suspend () -> T): MyResult<T> {
    return try {
        MyResult.Success(foo.invoke())
    } catch (e: Exception) {
        MyResult.Error(e)
    }
}

public inline fun <T> MyResult<T>.onFailure(action: (exception: Throwable) -> Unit): MyResult<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

public inline fun <T> MyResult<T>.onSuccess(action: (value: T) -> Unit): MyResult<T> {
    if (this is MyResult.Success) { action(this.data) }
    return this
}

inline fun <T> MyResult<T>.onSuccess(
    scope: CoroutineScope,
    crossinline action: (value: T) -> Unit
): MyResult<T> {
    scope.launch(Dispatchers.Main) {
        onSuccess(action)
    }
    return this
}

inline fun <T> MyResult<T>.onFailure(
    scope: CoroutineScope,
    crossinline action: (exception: Throwable) -> Unit
): MyResult<T> {
    scope.launch(Dispatchers.Main) {
        onFailure(action)
    }
    return this
}