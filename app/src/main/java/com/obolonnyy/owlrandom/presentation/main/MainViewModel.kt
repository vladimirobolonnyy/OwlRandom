package com.obolonnyy.owlrandom.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.orra.core_presentation.base.BaseViewModel

class MainViewModel(
    private val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel<MainViewEvent>() {

    private val _viewState = MutableLiveData<MainViewState>(MainViewState.Empty)
    val viewState: LiveData<MainViewState> = _viewState

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