package com.obolonnyy.owlrandom.presentation.activity

import androidx.lifecycle.LiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.model.MainTabs
import com.obolonnyy.owlrandom.repository.UserSettings
import com.obolonnyy.owlrandom.repository.UserSettingsImpl
import com.obolonnyy.owlrandom.utils.SingleLiveEvent

class MainActivityViewModel(
    settingsRepo: UserSettings = UserSettingsImpl(),
) : BaseViewModel() {

    private val _viewEvents = SingleLiveEvent<MainActivityViewEvent>()
    val viewEvents: LiveData<MainActivityViewEvent> = _viewEvents

    init {
        val event = when (settingsRepo.getMainTab()) {
            MainTabs.RANDOM -> MainActivityViewEvent.GoToMain
            MainTabs.LEARN -> MainActivityViewEvent.GoToLanguage
            MainTabs.SETTINGS -> MainActivityViewEvent.GoToSettings
        }
        _viewEvents.postValue(event)
    }
}