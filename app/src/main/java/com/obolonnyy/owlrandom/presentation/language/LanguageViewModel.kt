package com.obolonnyy.owlrandom.presentation.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.core.ProxyCacheServer
import com.obolonnyy.owlrandom.core.UriProxyCacheServer
import com.obolonnyy.owlrandom.domain.WordsInteractor
import com.obolonnyy.owlrandom.model.GooglePicture
import com.obolonnyy.owlrandom.model.LanguageImages
import com.obolonnyy.owlrandom.repository.UserSettings
import com.obolonnyy.owlrandom.repository.UserSettingsImpl
import com.obolonnyy.owlrandom.utils.SingleLiveEvent
import com.obolonnyy.owlrandom.utils.asResult
import com.obolonnyy.owlrandom.utils.onSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class LanguageViewModel(
    private val wordsInteractor: WordsInteractor = WordsInteractor(),
    private val settingsRepo: UserSettings = UserSettingsImpl(),
    private val imageCacheServer: ProxyCacheServer = UriProxyCacheServer()
) : BaseViewModel() {

    private val answered = mutableSetOf<Int>()
    private val notAnswered = mutableSetOf<Int>()

    private val _viewState = MutableLiveData<LanguageViewState>()
    val viewState: LiveData<LanguageViewState> = _viewState

    private val _pictureState = MutableLiveData<LanguageImages?>()
    val pictureState: LiveData<LanguageImages?> = _pictureState

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
        loadPictures(newState.pictures)
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
        val newState = state.prev(answered = answered.size, notAnswered = notAnswered.size)
        _viewState.postValue(newState)
        loadPictures(newState.pictures)
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
                loadPictures(it.first().googlePicture)
            }.onFailureUI {
                _viewEvents.postValue(LanguageViewEvent.Error(it))
            }
        }
    }

    private fun loadPictures(googlePicture: GooglePicture) {
        launchIO {
            asResult {
                val first = imageCacheServer.getProxyUrl(googlePicture.picture1Url, googlePicture.picture1)
                val second = imageCacheServer.getProxyUrl(googlePicture.picture2Url, googlePicture.picture2)
                val third = imageCacheServer.getProxyUrl(googlePicture.picture3Url, googlePicture.picture3)
                LanguageImages(first, second, third)
            }.onSuccess {
                _pictureState.postValue(it)
            }.onFailureUI {
                _pictureState.postValue(null)
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