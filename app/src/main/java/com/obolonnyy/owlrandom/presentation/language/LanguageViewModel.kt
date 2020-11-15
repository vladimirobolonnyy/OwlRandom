package com.obolonnyy.owlrandom.presentation.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.domain.WordsInteractor
import com.obolonnyy.owlrandom.model.Word
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import com.obolonnyy.owlrandom.utils.asResult
import com.obolonnyy.owlrandom.utils.onSuccess

class LanguageViewModel(
    private val wordsInteractor: WordsInteractor = WordsInteractor()
) : BaseViewModel() {

    private val words = mutableListOf<Word>()

    private val _viewState = MutableLiveData<LanguageViewState>()
    val viewState: LiveData<LanguageViewState> = _viewState

    private val _viewEvents = SingleLiveEvent<Throwable>()
    val viewEvents: LiveData<Throwable> = _viewEvents

    fun onSwitchClicked() {
        var state = _viewState.value ?: return
        state = state.copy(showEngTop = !state.showEngTop)
        _viewState.postValue(state)
    }

    fun onTranslationClicked() {
        var state = _viewState.value ?: return
        state = state.copy(showBottom = true)
        _viewState.postValue(state)
    }

    fun onNextClicked() {
        val state = _viewState.value ?: return
        if (state.currentItem + 1 >= state.maxItems)  {

            return
            // todo show sucess message
        }
        val word = words[state.currentItem + 1]
        val newState = LanguageViewState(word, state.currentItem + 1, words.size)
        _viewState.postValue(newState)
    }

    fun onRevertClicked() {
        val state = _viewState.value ?: return
        if (state.currentItem <= 0) return
        val word = words[state.currentItem - 1]
        val newState = LanguageViewState(word, state.currentItem - 1, words.size)
        _viewState.postValue(newState)
    }

    init {
        launchIO {
            asResult {
                wordsInteractor.invoke()
            }.onSuccess {
                words.addAll(it)
                val word = words[0]
                val state = LanguageViewState(word, 0, words.size)
                _viewState.postValue(state)
            }.onFailureUI {
                _viewEvents.postValue(it)
            }
        }
    }
}