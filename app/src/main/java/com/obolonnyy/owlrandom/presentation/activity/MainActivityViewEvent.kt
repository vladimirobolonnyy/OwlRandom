package com.obolonnyy.owlrandom.presentation.activity


sealed class MainActivityViewEvent {
    object GoToMain : MainActivityViewEvent()
    object GoToSettings : MainActivityViewEvent()
    object GoToLanguage : MainActivityViewEvent()
    object GoToStats : MainActivityViewEvent()
}