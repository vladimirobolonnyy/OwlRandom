package com.obolonnyy.owlrandom.presentation.coin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.presentation.details.random.Randomizer
import com.orra.core_presentation.base.BaseViewModel
import com.orra.core_presentation.utils.launch
import kotlinx.coroutines.delay

class CoinViewModel(
    private val randomizer: Randomizer = Randomizer(),
) : BaseViewModel<CoinViewEvent>() {

    private val _viewState = MutableLiveData(CoinViewState())
    val viewState: LiveData<CoinViewState> = _viewState

    fun roll() {
        val oldState = _viewState.value ?: return
        if (oldState.loading) return
        val result = randomizer.getFrom(1, 10) > 5
        val stats = _viewState.value?.stats.orEmpty()
        val newStats = stats + result
        _viewState.value = oldState.copy(loading = true)
        launch {
            if (oldState.useAnimation) {
                delay(1000)
            }
            _viewState.value = oldState.copy(value = result, loading = false, stats = newStats)
        }
    }

    fun onAnimationClicked() {
        val oldState = _viewState.value ?: return
        _viewState.value = oldState.copy(useAnimation = oldState.useAnimation.not())
    }

}