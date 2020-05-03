package com.obolonnyy.owlrandom.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import com.obolonnyy.owlrandom.utils.safeRemoveLast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class CreateDetailsViewModel(
    private val groupId: Long,
    private val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel() {

    private var state: CreateDetailsViewState = CreateDetailsViewState.Empty
    private val _viewState = MutableLiveData(state)
    val viewState: LiveData<CreateDetailsViewState> = _viewState

    private val _viewEvents = SingleLiveEvent<CreateDetailsViewEvent>()
    val viewEvents: LiveData<CreateDetailsViewEvent> = _viewEvents

    init {
        loadData()
    }

    fun onTitleChanged(newText: String) {
        (state as? CreateDetailsViewState.Loaded)?.let {
            it.copy(title = newText).post()
        }
    }

    fun onItemChanged(newText: String, item: CreateDetailsAdapterItem) {
        val state = (state as? CreateDetailsViewState.Loaded) ?: return
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
        val state = (state as? CreateDetailsViewState.Loaded) ?: return
        launchIO {
            repo.deleteGroup(state.toGroup(groupId))
            CreateDetailsViewState.Empty.post()
            _viewEvents.postValue(CreateDetailsViewEvent.NavigateToMain)
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
                CreateDetailsViewState.Loaded(newGroup).post()
            } else {
                CreateDetailsViewState.Loaded("", mutableListOf(CreateDetailsAdapterItem(0, ""))).post()
            }
        }
    }

    override fun onCleared() {
        val state = (state as? CreateDetailsViewState.Loaded) ?: return
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
