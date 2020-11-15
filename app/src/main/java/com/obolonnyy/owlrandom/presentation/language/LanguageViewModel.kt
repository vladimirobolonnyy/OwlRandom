package com.obolonnyy.owlrandom.presentation.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.UserSettings
import com.obolonnyy.owlrandom.database.UserSettingsImpl
import com.obolonnyy.owlrandom.domain.WordsInteractor
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import com.obolonnyy.owlrandom.utils.asResult
import com.obolonnyy.owlrandom.utils.onSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class LanguageViewModel(
    private val wordsInteractor: WordsInteractor = WordsInteractor(),
    private val settingsRepo: UserSettings = UserSettingsImpl()
) : BaseViewModel() {

    private val answered = mutableSetOf<Int>()
    private val notAnswered = mutableSetOf<Int>()

    private val _viewState = MutableLiveData<LanguageViewState>()
    val viewState: LiveData<LanguageViewState> = _viewState

    private val _viewEvents = SingleLiveEvent<LanguageViewEvent>()
    val viewEvents: LiveData<LanguageViewEvent> = _viewEvents

    private val _timerEvents = MutableLiveData<LanguageTimerState>()
    val timerEvents: LiveData<LanguageTimerState> = _timerEvents
    private var job: Job? = null

    init {
        load()
        loadTimer()
    }

    fun onSwitchClicked() {
        var state = _viewState.value ?: return
        state = state.copy(showEngTop = !state.showEngTop)
        _viewState.postValue(state)
    }

    fun onTranslationClicked() {
        val state = _viewState.value ?: return
        if (state.isFull) return
        _viewState.postValue(state.copy(showBottom = true))
    }

    fun onSkipClicked() {
        val state = _viewState.value ?: return
        notAnswered.add(state.currentItem)
        answered.remove(state.currentItem)
        onNext(state)
    }

    fun onNextClicked() {
        val state = _viewState.value ?: return
        answered.add(state.currentItem)
        notAnswered.remove(state.currentItem)
        onNext(state)
    }

    private fun onNext(state: LanguageViewState) {
        val newState = state.next(answered = answered.size, notAnswered = notAnswered.size)
        _viewState.postValue(newState)
        checkFullState(newState)
    }

    private fun checkFullState(state: LanguageViewState) {
        if (state.isFull) {
            _viewEvents.postValue(
                LanguageViewEvent.Retry(
                    answered = answered.size,
                    notAnswered = notAnswered.size
                )
            )
        }
    }

    fun onRevertClicked() {
        val state = _viewState.value ?: return
        answered.remove(state.currentItem)
        answered.remove(state.currentItem - 1)
        notAnswered.remove(state.currentItem)
        notAnswered.remove(state.currentItem - 1)
        _viewState.postValue(state.prev(answered = answered.size, notAnswered = notAnswered.size))
    }

    fun reload() {
        answered.clear()
        notAnswered.clear()
        load()
    }

    private fun load() {
        launchIO {
            asResult {
                wordsInteractor.invoke()
            }.onSuccess {
                _viewState.postValue(LanguageViewState(it))
            }.onFailureUI {
                _viewEvents.postValue(LanguageViewEvent.Error(it))
            }
        }
    }

    private fun loadTimer() {
        val time = settingsRepo.getTodaySpendSeconds()
        _timerEvents.postValue(LanguageTimerState(time))
    }

    fun startTimer() {
        job?.cancel()
        job = launchIO {
            val time = settingsRepo.getTodaySpendSeconds()
            for (t in time..Long.MAX_VALUE) {
                _timerEvents.postValue(LanguageTimerState(t))
                delay(1000)
            }
        }
    }

    fun onPause() {
        val counted = _timerEvents.value?.seconds ?: 0L
        settingsRepo.saveCurrentSeconds(counted)
        job?.cancel()
    }
}