package com.obolonnyy.owlrandom.presentation.main

import com.obolonnyy.owlrandom.model.MyGroup

sealed class MainViewState {
    object Empty : MainViewState()
    data class Loaded(val groups: List<MyGroup>) : MainViewState()
}

sealed class MainViewEvent {
    object GoToCreateItem : MainViewEvent()
}