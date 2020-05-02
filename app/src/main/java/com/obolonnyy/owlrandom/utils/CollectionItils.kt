package com.obolonnyy.owlrandom.utils
fun <T> MutableList<T>.safeRemoveLast(): T? = if (isEmpty()) null else removeAt(lastIndex)

fun <T> MutableList<T>.clearAndAdd(newList: List<T>): MutableList<T>  {
    this.clear()
    this.addAll(newList)
    return this
}
