package com.obolonnyy.owlrandom.presentation.main

import com.obolonnyy.owlrandom.model.MyGroup

sealed class MainViewState {
}

sealed class MainViewEvent {
    object GoToCreateItem : MainViewEvent()
}