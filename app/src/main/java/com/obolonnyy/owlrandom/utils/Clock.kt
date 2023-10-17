package com.obolonnyy.owlrandom.utils

interface Clock {
    fun nowSeconds(): Long
}

class RealtimeClock : Clock {
    override fun nowSeconds() = System.currentTimeMillis() / 1000

}