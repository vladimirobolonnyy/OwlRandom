package com.obolonnyy.owlrandom.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.model.MyGroup
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import com.obolonnyy.owlrandom.utils.asResult
import com.obolonnyy.owlrandom.utils.safeRemoveLast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class EditDetailsViewModel(
    private val groupId: Long?,
    private val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel() {

    private val _viewState = MutableLiveData<CreateDetailsViewState>(CreateDetailsViewState.Empty)
    private val state: CreateDetailsViewState get() = _viewState.value!!
    val viewState: LiveData<CreateDetailsViewState> = _viewState

    private val _viewEvents = SingleLiveEvent<CreateDetailsViewEvent>()
    val viewEvents: LiveData<CreateDetailsViewEvent> = _viewEvents

    init {
        loadData()
    }

    fun onTitleChanged(newText: String) {
        (state as? CreateDetailsViewState.Loaded)?.copy(title = newText)?.post()
    }

    fun onItemChanged(newText: String, item: EditDetailsAdapterItem) {
        val state = (state as? CreateDetailsViewState.Loaded) ?: return
        val oldText = state.list[item.position].text
        state.list[item.position] =
            state.list[item.position].copy(text = newText, requestFocus = true)
        if (oldText.isNotBlank() && newText.isBlank()) {
            state.list.safeRemoveLast()
        }
        if (oldText.isBlank() && newText.isNotBlank()) {
            state.list.add(EditDetailsAdapterItem(position = state.list.size))
        }
        state.post()
    }

    fun delete() {
        val state = (state as? CreateDetailsViewState.Loaded) ?: return
        launchIO {
            repo.deleteGroup(state.getGroup())
            CreateDetailsViewState.Empty.post()
            _viewEvents.postValue(CreateDetailsViewEvent.NavigateToMain)
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
                CreateDetailsViewState.Loaded(newGroup).post()
            }
        }
    }

    override fun onCleared() {
        val state = (state as? CreateDetailsViewState.Loaded) ?: return
        GlobalScope.launch(Dispatchers.IO) {
            val group = state.getGroup()
            if (group.items.all { it.isEmpty() }) {
                repo.deleteGroup(group)
                Timber.i("CreateDetailsViewModel deleted group with id:= ${group.id}")
            } else {
                repo.saveGroup(group)
                Timber.i("CreateDetailsViewModel saved group with id:= ${group.id}")
            }
        }
        super.onCleared()
    }

    private fun CreateDetailsViewState.Loaded.getGroup(): MyGroup {
        return MyGroup(
            id = this.groupId,
            title = this.title,
            items = this.list.filter { it.text.isNotBlank() }.map { it.text }
        )
    }

    private fun CreateDetailsViewState.post() {
        _viewState.postValue(this)
    }
}
