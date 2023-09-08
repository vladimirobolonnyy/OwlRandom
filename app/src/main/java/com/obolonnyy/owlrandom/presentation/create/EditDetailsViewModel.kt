package com.obolonnyy.owlrandom.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.app.appScope
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.model.MyGroup
import com.orra.core_presentation.base.BaseViewModel
import com.orra.core_presentation.utils.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class EditDetailsViewModel(
    private val groupId: Long?,
    private val repo: MainRepository = MainRepositoryImpl(),
    private val applicationScope: CoroutineScope = appScope
) : BaseViewModel<CreateDetailsViewEvent>() {

    private val _viewState = MutableLiveData(CreateDetailsViewState())
    private val state: CreateDetailsViewState get() = _viewState.value!!
    val viewState: LiveData<CreateDetailsViewState> = _viewState

    init {
        loadData()
    }

    fun onTitleChanged(newText: String) {
        state.copy(title = newText).set()
    }

    fun onItemsChanged(newText: String) {
        state.copy(items = newText).set()
    }

    fun delete() {
        launchIO {
            asResult {
                repo.deleteGroup(state.getGroup())
            }.onSuccess {
                CreateDetailsViewState().set()
                _viewEvents.postValue(CreateDetailsViewEvent.NavigateToMain)
            }
        }
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
                CreateDetailsViewState(newGroup).set()
            }
        }
    }

    fun saveItems() {
        applicationScope.launch(Dispatchers.IO) {
            val group = state.getGroup()
            if (group.items.all { it.isEmpty() }) {
                repo.deleteGroup(group)
                Timber.i("CreateDetailsViewModel deleted group with id:= ${group.id}")
            } else {
                repo.saveGroup(group)
                Timber.i("CreateDetailsViewModel saved group with id:= ${group.id}")
            }
        }
    }

    private fun CreateDetailsViewState.getGroup(): MyGroup {
        return MyGroup(
            id = this.groupId,
            title = this.title,
            items = this.items.split("\n").filter { it.isNotEmpty() }
        )
    }

    private fun CreateDetailsViewState.set() {
        _viewState.value = this
    }
}
