package com.obolonnyy.owlrandom.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import kotlinx.coroutines.flow.collect

class DetailsViewModel(
    private val groupId: Long,
    private val repo: MainRepositoryImpl = MainRepositoryImpl()
) : BaseViewModel() {

    private var state: DetailsViewState = DetailsViewState.Loading
    private val _viewState = MutableLiveData<DetailsViewState>()
    val viewState: LiveData<DetailsViewState> = _viewState

    init {
        loadData()
    }

    private fun loadData() {
        launchIO {
            DetailsViewState.Loading.post()
            repo.getFlowGroup(groupId).collect { group ->
                val adapterItems = group.items.mapIndexed { i, s -> DetailsAdapterItem(i, s) }
                DetailsViewState.Loaded(group, adapterItems).post()
            }
        }
    }

    private fun DetailsViewState.post() {
        state = this
        _viewState.postValue(state)
    }
}
