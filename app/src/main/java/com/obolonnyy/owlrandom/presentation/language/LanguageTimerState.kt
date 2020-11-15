package com.obolonnyy.owlrandom.presentation.language

data class LanguageTimerState(
    val seconds: Long
) {
    fun getHumanTime(): String {
        val seconds = addZeroIfNeeded(seconds % 60)
        val minutes = addZeroIfNeeded((this.seconds / 60) % 60)
        val hours = addZeroIfNeeded((this.seconds / 3600) % 60)
        return if (hours == "00") {
            "$minutes:$seconds"
        } else {
            "$hours:$minutes:$seconds"
        }
    }

    private fun addZeroIfNeeded(long: Long): String {
        return if (long < 10) "0$long" else long.toString()
    }
}
