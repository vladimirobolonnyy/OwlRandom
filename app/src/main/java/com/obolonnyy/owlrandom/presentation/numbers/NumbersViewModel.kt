package com.obolonnyy.owlrandom.presentation.numbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.presentation.details.random.Randomizer
import com.obolonnyy.owlrandom.utils.digitsOnly
import com.orra.core_presentation.base.BaseViewModel

class NumbersViewModel(
    private val randomizer: Randomizer = Randomizer(),
) : BaseViewModel<DiceViewEvent>() {

    private val _viewState = MutableLiveData(NumbersViewState())
    val viewState: LiveData<NumbersViewState> = _viewState

    fun roll() {
        val state = _viewState.value ?: return
        val minValue = state.minValue ?: 1
        val maxValue = state.maxValue ?: 1
        val results = randomizer.getFrom(minValue, maxValue + 1)
        val stats = _viewState.value?.stats.orEmpty()
        val newStats = stats.toMutableList().apply { add(results) }
        _viewState.value = state.copy(
            result = results,
            stats = newStats,
            minValueStr = minValue.toString(),
            maxValueStr = maxValue.toString()
        )
    }

    fun onMaxValueChanged(s: String) {
        val state = _viewState.value ?: return
        _viewState.value = state.copy(maxValueStr = s.digitsOnly())
    }

    fun onMinValueChanged(s: String) {
        val state = _viewState.value ?: return
        _viewState.value = state.copy(minValueStr = s.digitsOnly())
    }
}