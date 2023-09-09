package com.obolonnyy.owlrandom.presentation.dice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.presentation.details.random.Randomizer
import com.orra.core_presentation.base.BaseViewModel
import kotlin.math.max

class DiceViewModel(
    private val randomizer: Randomizer = Randomizer(),
) : BaseViewModel<DiceViewEvent>() {

    private val _viewState = MutableLiveData(DiceViewState())
    val viewState: LiveData<DiceViewState> = _viewState


    fun roll() {
        val state = _viewState.value ?: return
        val results = (0 until state.dicesNumber).toList().map {
            randomizer.getFrom(1, state.maxValue + 1)
        }
        val stats = _viewState.value?.stats.orEmpty()
        val newStats = stats.toMutableList().apply { add(results) }
        _viewState.value = state.copy(
            values = results,
            stats = newStats
        )
    }

    fun onMaxValueChanged(s: String) {
        val state = _viewState.value ?: return
        _viewState.value = state.copy(
            maxValue = max(s.toInt(), 2),
        )
    }

    fun onDicesNumberChanged(s: String) {
        val state = _viewState.value ?: return
        _viewState.value = state.copy(
            dicesNumber = s.toInt(),
        )
    }

    private fun String.toInt(): Int {
        return this.toIntOrNull().takeIf { (it ?: 0) > 0 } ?: 1
    }

}