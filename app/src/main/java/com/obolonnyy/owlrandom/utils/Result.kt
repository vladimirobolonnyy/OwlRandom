package com.obolonnyy.owlrandom.utils

sealed class MyResult<out T> {
    data class Success<T>(val data: T) : MyResult<T>()
    data class Error(val err: Throwable) : MyResult<Nothing>()
}

suspend fun <T> asResult(foo: suspend () -> T): MyResult<T> {
    return try {
        MyResult.Success(foo.invoke())
    } catch (e: Exception) {
        MyResult.Error(e)
    }
}

//fun <T> asResult(foo: () -> T): MyResult<T> {
//    return try {
//        MyResult.Success(foo.invoke())
//    } catch (e: Exception) {
//        MyResult.Error(e)
//    }
//}