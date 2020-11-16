package com.obolonnyy.owlrandom.utils

import kotlinx.coroutines.*

/**
 * Запускаем ассинхронно список запросов для списка айдишников [list].
 *
 * Пример:
 * asyncAll(someList) { api.getImageByUrl(it) }.awaitAll().forEach { print(it) }
 */
suspend fun <T, V> asyncAll(list: List<T>, block: suspend (T) -> V): List<V> {
    return coroutineScope {
        list.map { async { block.invoke(it) } }.awaitAll()
    }
}

suspend fun <T, V> asyncAllSuccessful(list: List<T>, block: suspend (T) -> V): List<V> {
    return asyncAllNotNull(list) {
        try {
            block.invoke(it)
        } catch (e: Exception) {
            null
        }
    }
}

suspend fun <T, V> asyncAllNotNull(list: List<T>, block: suspend (T) -> V?): List<V> = coroutineScope {
    list.map { async { block.invoke(it) } }.awaitAll().mapNotNull { it }
}

fun CoroutineScope.launchAndForget(block: suspend () -> Unit) {
    launch(Dispatchers.IO) {
        runCatching { block.invoke() }
    }
}