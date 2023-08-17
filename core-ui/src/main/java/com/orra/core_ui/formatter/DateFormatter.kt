package com.orra.core_ui.formatter

import org.joda.time.LocalDate


enum class DatePattern(val pattern: String) {
    // 17.05.2022
    DEFAULT("dd.MM.yyyy"),

    //17.05.22
    DEFAULT_SHORT_YEAR("dd.MM.yy"),
}

interface DateFormatter {

    fun formatDate(date: LocalDate): String

    fun formatDate(date: LocalDate, format: DatePattern): String

}