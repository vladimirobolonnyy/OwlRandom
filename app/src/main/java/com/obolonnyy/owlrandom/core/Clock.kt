package com.obolonnyy.owlrandom.core

interface Clock {
    fun nowSeconds(): Long
    fun today(): Long
}

class RealtimeClock : Clock {
    override fun nowSeconds() = System.currentTimeMillis() / 1000
    override fun today(): Long = nowSeconds() / (60 * 60 * 24)
}