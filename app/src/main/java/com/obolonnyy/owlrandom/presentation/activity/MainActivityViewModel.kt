package com.obolonnyy.owlrandom.presentation.activity

import com.orra.core_presentation.base.BaseViewModel

class MainActivityViewModel : BaseViewModel<MainActivityViewEvent>() {

    init {
        _viewEvents.postValue(MainActivityViewEvent.GoToMain)
    }
}