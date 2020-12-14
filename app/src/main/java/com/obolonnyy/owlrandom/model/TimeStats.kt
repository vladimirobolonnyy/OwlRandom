package com.obolonnyy.owlrandom.model

import kotlinx.datetime.LocalDate

data class TimeStats(
    val date: LocalDate,
    val workedSeconds: Long
)