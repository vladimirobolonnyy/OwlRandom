package com.obolonnyy.owlrandom.core

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import java.util.*

interface Clock {
    fun nowSeconds(): Long
    fun today(): Long
    fun localDate(): LocalDate
}

class RealtimeClock : Clock {
    override fun nowSeconds() = System.currentTimeMillis() / 1000
    override fun today(): Long = nowSeconds() / (60 * 60 * 24)
    override fun localDate(): LocalDate {
        val javaDate = Date(System.currentTimeMillis())
        return LocalDate(javaDate.year + 1900, javaDate.month + 1, javaDate.date)
    }
}