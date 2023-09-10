package com.obolonnyy.owlrandom.presentation.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.orra.core_presentation.base.BaseViewModel

class GroupsViewModel(
    private val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel<GroupsViewEvent>() {

    private val _viewState = MutableLiveData(GroupsViewState())
    val viewState: LiveData<GroupsViewState> = _viewState

    init {
        loadData()
    }

    fun onAddItemClicked() {
        _viewEvents.postValue(GroupsViewEvent.GoToCreateItem)
    }

    private fun loadData() = launchIO {
        repo.getAllGroups()
            .collect { groups ->
                _viewState.postValue(GroupsViewState(groups))
            }
    }
}