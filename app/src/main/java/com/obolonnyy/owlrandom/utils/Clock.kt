package com.obolonnyy.owlrandom.utils

import org.joda.time.DateTime
import org.joda.time.LocalDate

interface Clock {
    fun nowSeconds(): Long

    fun now(): DateTime

    fun today(): LocalDate
}

class RealtimeClock : Clock {
    override fun today(): LocalDate = LocalDate.now()

    override fun now(): DateTime = DateTime.now()

    override fun nowSeconds() = System.currentTimeMillis() / 1000

}