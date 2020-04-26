package com.obolonnyy.owlrandom.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.utils.safeRemoveLast


class CreateDetailsViewModel : BaseViewModel() {

    private var state = createDefaultList()
    private val _viewState = MutableLiveData(state)
    val viewState: LiveData<CreateDetailsViewState> = _viewState

    private fun createDefaultList(): CreateDetailsViewState {
        return CreateDetailsViewState(
            list = listOf(CreateDetailsAdapterItem(0, ""))
        )
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
