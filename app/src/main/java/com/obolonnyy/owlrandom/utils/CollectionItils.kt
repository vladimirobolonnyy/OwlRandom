package com.obolonnyy.owlrandom.utils

public fun <T> MutableList<T>.safeRemoveLast(): T? = if (isEmpty()) null else removeAt(lastIndex)
