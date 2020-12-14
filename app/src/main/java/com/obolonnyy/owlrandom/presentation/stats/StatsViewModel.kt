package com.obolonnyy.owlrandom.presentation.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.STATS_DATABASE
import com.obolonnyy.owlrandom.database.StatsDatabase
import com.obolonnyy.owlrandom.database.toModel
import com.obolonnyy.owlrandom.utils.asResult
import com.obolonnyy.owlrandom.utils.mapDistinct
import com.obolonnyy.owlrandom.utils.warning

class StatsViewModel(
    private val stateDataBase: StatsDatabase = STATS_DATABASE,
) : BaseViewModel() {

    private val _viewState = MutableLiveData<StatsViewState>()
    val viewState: LiveData<StatsViewState> = _viewState
    val list = _viewState.mapDistinct { it.stats }

    init {
        loadStats()
    }

    private fun loadStats() = launchIO {
        asResult {
            stateDataBase.statsDao().getAll()
                ?.map { it.toModel() }
                ?.sortedByDescending { it.date }
        }.onSuccessUI { list ->
            _viewState.value = StatsViewState(list ?: emptyList())
        }.onFailureUI {
            warning(it)
        }
    }
}