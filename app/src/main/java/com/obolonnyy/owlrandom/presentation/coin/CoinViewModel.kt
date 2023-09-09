package com.obolonnyy.owlrandom.presentation.coin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.presentation.details.random.Randomizer
import com.orra.core_presentation.base.BaseViewModel

class CoinViewModel(
    private val randomizer: Randomizer = Randomizer(),
) : BaseViewModel<CoinViewEvent>() {

    private val _viewState = MutableLiveData(CoinViewState())
    val viewState: LiveData<CoinViewState> = _viewState


    fun roll() {
        val result = randomizer.getBool()
        val stats = _viewState.value?.stats.orEmpty()
        val newStats = stats + result
        _viewState.value = CoinViewState(result, newStats)
    }

}