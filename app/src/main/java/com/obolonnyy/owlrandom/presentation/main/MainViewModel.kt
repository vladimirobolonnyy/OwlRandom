package com.obolonnyy.owlrandom.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl

class MainViewModel(
    private val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel() {

    private val _viewState = MutableLiveData<MainViewState>(MainViewState.Empty)
    val viewState: LiveData<MainViewState> = _viewState

    init {
        loadData()
    }

    fun onStart() {
        loadData()
    }

    private fun loadData() = launchIO {
        val groups = repo.getAllGroups()
        if (groups.isNotEmpty()) {
            _viewState.postValue(MainViewState.Loaded(groups))
        }
    }
}