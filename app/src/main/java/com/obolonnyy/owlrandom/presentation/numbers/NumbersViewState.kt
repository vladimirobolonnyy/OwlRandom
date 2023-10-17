package com.obolonnyy.owlrandom.presentation.numbers

import androidx.compose.runtime.Stable


@Stable
data class NumbersViewState(
    val minValueStr: String = "0",
    val maxValueStr: String = "1000",
    val result: Long? = null,
    val stats: List<Long> = emptyList()
) {

    val minValue: Long? = minValueStr.toLongOrNull()
    val maxValue: Long? = maxValueStr.toLongOrNull()

}

sealed class DiceViewEvent {
}