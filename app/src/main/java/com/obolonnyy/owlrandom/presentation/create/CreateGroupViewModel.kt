package com.obolonnyy.owlrandom.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.app.appScope
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.model.MyGroup
import com.obolonnyy.owlrandom.utils.log
import com.orra.core_presentation.base.BaseViewModel
import com.orra.core_presentation.utils.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateGroupViewModel(
    private val groupId: Long?,
    private val repo: MainRepository = MainRepositoryImpl(),
    private val applicationScope: CoroutineScope = appScope
) : BaseViewModel<CreateGroupViewEvent>() {

    private val _viewState = MutableLiveData(CreateGroupViewState())
    private val state: CreateGroupViewState get() = _viewState.value!!
    val viewState: LiveData<CreateGroupViewState> = _viewState

    init {
        loadData()
    }

    fun onTitleChanged(newText: String) {
        state.copy(title = newText).set()
    }

    fun onItemsChanged(newText: String) {
        state.copy(items = newText).set()
    }

    fun onDeleteClicked() {
        CreateGroupViewEvent.ShowDeleteDialog.post()
    }

    fun onSaveClicked() {
        saveItems()
        CreateGroupViewEvent.NavigateBack.post()
    }

    fun onDeleteConfirmed() {
        launchIO {
            asResult {
                repo.deleteGroup(state.getGroup())
            }.onSuccessUI {
                CreateGroupViewState().set()
                CreateGroupViewEvent.NavigateToGroups.post()
            }.onFailureUI {
                showErrorMessage()
            }
        }
    }

    fun onStop() {
        saveItems()
    }

    private fun saveItems() {
        val state = _viewState.value ?: return
        val title = state.title.takeIf { it.isNotEmpty() } ?: "No name"
        onTitleChanged(title)
        onItemsChanged(state.items)
        saveItems(state)
    }


    private fun loadData() {
        launchIO {
            asResult {
                repo.getGroup(groupId) ?: repo.createEmptyGroup()
            }.onSuccessUI { group ->
                val groupItems = group.items.toMutableList()
                if (groupItems.lastOrNull().isNullOrBlank().not() || groupItems.isEmpty()) {
                    groupItems.add("")
                }
                val newGroup = group.copy(items = groupItems)
                CreateGroupViewState(newGroup).set()
            }
        }
    }

    private fun saveItems(state: CreateGroupViewState) {
        applicationScope.launch(Dispatchers.IO) {
            val group = state.getGroup()
            if (group.strItems.isEmpty() || state.isEmpty) {
                repo.deleteGroup(group)
                log("CreateDetailsViewModel deleted group with id:= ${group.id}")
            } else {
                repo.saveGroup(group)
                log("CreateDetailsViewModel saved group with id:= ${group.id}")
            }
        }
    }

    private fun CreateGroupViewState.getGroup(): MyGroup {
        return MyGroup(
            id = this.groupId,
            title = this.title,
            items = this.items.split("\n").filter { it.isNotEmpty() }
        )
    }

    private fun CreateGroupViewState.set() {
        _viewState.value = this
    }

}
