package com.obolonnyy.owlrandom.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val repo: MainRepositoryImpl = MainRepositoryImpl()
) : BaseViewModel() {

    private val _viewState = MutableLiveData<MainViewState>(MainViewState.Empty)
    val viewState: LiveData<MainViewState> = _viewState

    init {
        loadData()
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