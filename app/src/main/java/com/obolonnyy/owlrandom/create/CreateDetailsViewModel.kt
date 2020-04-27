package com.obolonnyy.owlrandom.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.model.Group
import com.obolonnyy.owlrandom.utils.safeRemoveLast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateDetailsViewModel(
    val groupId: Long = 1,
    val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel() {

    private var state: CreateDetailsViewState = createDefaultList()
    private val _viewState = MutableLiveData(state)
    val viewState: LiveData<CreateDetailsViewState> = _viewState

    private fun createDefaultList(): CreateDetailsViewState {
        return CreateDetailsViewState(Group(groupId, "", listOf("")))
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val group = repo.getGroup(groupId)
            group?.let {
                state = CreateDetailsViewState(it)
                post()
            }
        }
    }

    fun onPause() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(group = state.group.copy(items = state.list.map { it.text }))
            repo.saveGroup(state.group)
            val allGroup = repo.getAllGroups()
            print("#### $allGroup")
        }
    }

    fun onTitleChanged(newText: String) {
        state = state.copy(group = state.group.copy(title = newText))
    }

    fun onItemChanged(newText: String, item: CreateDetailsAdapterItem) {
        //ToDo save in room
        if (state.list[item.number].text == newText) {
            return
        }
        val list = state.list.toMutableList()

        if (list[item.number].text.isNotBlank() && newText.isBlank()) {
            list.safeRemoveLast()
            list[item.number] = list[item.number].copy(text = newText, requestFocus = true)
            state = state.copy(list = list)
            post()
            return
        }

        if (list[item.number].text.isBlank() && newText.isNotBlank()) {
            list.add(CreateDetailsAdapterItem(number = list.size))
            list[item.number] = list[item.number].copy(text = newText, requestFocus = true)
            state = state.copy(list = list)
            post()
            return
        }
        list[item.number] = list[item.number].copy(text = newText, requestFocus = false)
        state = state.copy(list = list)
    }

    private fun post() {
        _viewState.postValue(state)
    }
}
