package com.obolonnyy.owlrandom.presentation.activity

import androidx.lifecycle.LiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.utils.SingleLiveEvent

class MainActivityViewModel : BaseViewModel() {

    private val _viewEvents = SingleLiveEvent<MainActivityViewEvent>()
    val viewEvents: LiveData<MainActivityViewEvent> = _viewEvents

    init {
        _viewEvents.postValue(MainActivityViewEvent.GoToMain)
    }
}