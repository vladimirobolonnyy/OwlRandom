package com.obolonnyy.owlrandom.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import com.obolonnyy.owlrandom.utils.safeRemoveLast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class CreateDetailsViewModel(
    private val groupId: Long,
    private val repo: MainRepositoryImpl = MainRepositoryImpl()
) : BaseViewModel() {

    private var state: CreateDetailsViewState = CreateDetailsViewState()
    private val _viewState = MutableLiveData(state)
    val viewState: LiveData<CreateDetailsViewState> = _viewState

    private val _viewEvents = SingleLiveEvent<CreateDetailsViewEvent>()
    val viewEvents: LiveData<CreateDetailsViewEvent> = _viewEvents

    init {
        loadData()
    }

    fun onTitleChanged(newText: String) {
        state.copy(title = newText).post()
    }

    fun onItemChanged(newText: String, item: CreateDetailsAdapterItem) {
        val oldText = state.list[item.position].text
        state.list[item.position] =
            state.list[item.position].copy(text = newText, requestFocus = true)
        if (oldText.isNotBlank() && newText.isBlank()) {
            state.list.safeRemoveLast()
        }
        if (oldText.isBlank() && newText.isNotBlank()) {
            state.list.add(CreateDetailsAdapterItem(position = state.list.size))
        }
        state.post()
    }

    fun delete() {
        launchIO {
            //ToDo add confirm dialog
            repo.deleteGroup(state.toGroup(groupId))
            _viewEvents.postValue(CreateDetailsViewEvent.NavigateBack)
        }
    }

    private fun loadData() {
        launchIO {
            val group = repo.getGroup(groupId)
            if (group != null) {
                val groupItems = group.items.toMutableList()
                if (groupItems.lastOrNull().isNullOrBlank().not() || groupItems.isEmpty()) {
                    groupItems.add("")
                }
                val newGroup = group.copy(items = groupItems)
                CreateDetailsViewState(newGroup).post()
            } else {
                CreateDetailsViewState("", mutableListOf(CreateDetailsAdapterItem(0, ""))).post()
            }
        }
    }

    override fun onCleared() {
        GlobalScope.launch(Dispatchers.IO) {
            val group = state.toGroup(groupId)
            if (group.items.any { it.isNotBlank() }) {
                repo.saveGroup(group)
                Timber.i("CreateDetailsViewModel saved group with id:= ${group.id}")
            }
        }
        super.onCleared()
    }

    private fun CreateDetailsViewState.post() {
        state = this
        _viewState.postValue(state)
    }
}
