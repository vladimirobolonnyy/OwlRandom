package com.obolonnyy.owlrandom.presentation.numbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.presentation.details.random.Randomizer
import com.orra.core_presentation.base.BaseViewModel
import kotlin.math.max

class NumbersViewModel(
    private val randomizer: Randomizer = Randomizer(),
) : BaseViewModel<DiceViewEvent>() {

    private val _viewState = MutableLiveData(NumbersViewState())
    val viewState: LiveData<NumbersViewState> = _viewState


    fun roll() {
        val state = _viewState.value ?: return
        val results = randomizer.getFrom(1, state.maxValue + 1)
        val stats = _viewState.value?.stats.orEmpty()
        val newStats = stats.toMutableList().apply { add(results) }
        _viewState.value = state.copy(
            values = results,
            stats = newStats
        )
    }

    //todo когда удаляешь из инпута значение - подставляется число, что выглядит криво

    fun onMaxValueChanged(s: String) {
        val state = _viewState.value ?: return
        _viewState.value = state.copy(
            maxValue = max(s.toLong(), 1),
        )
    }

    fun onMinValueChanged(s: String) {
        val state = _viewState.value ?: return
        _viewState.value = state.copy(
            minValue = max(s.toLong(), 2),
        )
    }

    private fun String.toLong(): Long {
        return this.toLongOrNull().takeIf { (it ?: 0L) > 0L } ?: 1L
    }

}