package com.obolonnyy.owlrandom.presentation.coin


data class CoinViewState(
    val value: Boolean = false,
    val loading: Boolean = false,
    val useAnimation: Boolean = true,
    val stats: List<Boolean> = emptyList()
) {
    val positive = stats.count { it }
    val negative = stats.count { !it }
}

sealed class CoinViewEvent {
}