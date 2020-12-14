package com.obolonnyy.owlrandom.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.repository.MainRepository
import com.obolonnyy.owlrandom.repository.MainRepositoryImpl
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel() {

    private val _viewState = MutableLiveData<MainViewState>(MainViewState.Empty)
    val viewState: LiveData<MainViewState> = _viewState

    private val _viewEvents = SingleLiveEvent<MainViewEvent>()
    val viewEvents: LiveData<MainViewEvent> = _viewEvents

    init {
        loadData()
    }

    fun onAddItemClicked() {
        _viewEvents.postValue(MainViewEvent.GoToCreateItem)
    }

    private fun loadData() = launchIO {
        repo.getAllGroups().collect { groups ->
            if (groups.isNotEmpty()) {
                _viewState.postValue(MainViewState.Loaded(groups))
            } else {
                _viewState.postValue(MainViewState.Empty)
            }
        }
    }
}