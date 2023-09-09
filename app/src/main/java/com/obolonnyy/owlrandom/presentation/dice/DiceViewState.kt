package com.obolonnyy.owlrandom.presentation.dice


data class DiceViewState(
    val dicesNumber: Int = 1,
    val maxValue: Int = 6,
    val values: List<Int> = emptyList(),
    val stats: List<List<Int>> = emptyList()
)

sealed class DiceViewEvent {
}