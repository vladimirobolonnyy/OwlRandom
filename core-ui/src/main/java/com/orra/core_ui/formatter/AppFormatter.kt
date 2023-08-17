package com.orra.core_ui.formatter

import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale

object AppFormatter : DateFormatter {

    private val locale = Locale("en", "EN")

    private val dateFormattersMap = mutableMapOf<String, SimpleDateFormat>()

    private fun getDateFormat(format: String): SimpleDateFormat {
        return dateFormattersMap[format] ?: SimpleDateFormat(format, locale).also {
            dateFormattersMap[format] = it
        }
    }

    override fun formatDate(date: LocalDate): String {
        val formatter = getDateFormat("dd.MM.yyyy")
        return formatter.format(date.toDate())
    }

    override fun formatDate(date: LocalDate, format: DatePattern): String {
        return getDateFormat(format.pattern).format(date.toDate())
    }
}