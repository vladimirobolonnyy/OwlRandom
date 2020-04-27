package com.obolonnyy.owlrandom.model

const val delimeter = 'δ'

interface Stringable<T : Any> {
    fun <T : Stringable<T>> toStr(): String
    fun from(str: String): T
}