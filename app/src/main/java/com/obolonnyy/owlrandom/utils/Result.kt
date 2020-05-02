package com.obolonnyy.owlrandom.utils

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val err: Throwable) : Result<Nothing>()
}