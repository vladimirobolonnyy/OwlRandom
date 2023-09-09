package com.obolonnyy.owlrandom.presentation.numbers


data class NumbersViewState(
    val minValue: Long = 1,
    val maxValue: Long = 1000,
    val values: Long = -1,
    val stats: List<Long> = emptyList()
)

sealed class DiceViewEvent {
}