package com.obolonnyy.owlrandom.presentation.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.domain.WordsInteractor
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import com.obolonnyy.owlrandom.utils.asResult
import com.obolonnyy.owlrandom.utils.onSuccess

class LanguageViewModel(
    private val wordsInteractor: WordsInteractor = WordsInteractor()
) : BaseViewModel() {

    private val _viewState = MutableLiveData<LanguageViewState>()
    val viewState: LiveData<LanguageViewState> = _viewState

    private val _viewEvents = SingleLiveEvent<Throwable>()
    val viewEvents: LiveData<Throwable> = _viewEvents

    init {
        launchIO {
            asResult {
                wordsInteractor.invoke()
            }.onSuccess {
                _viewState.postValue(LanguageViewState(it))
            }.onFailureUI {
                _viewEvents.postValue(it)
            }
        }
    }

}