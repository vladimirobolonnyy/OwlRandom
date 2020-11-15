package com.obolonnyy.owlrandom.presentation.language

sealed class LanguageViewEvent {
    data class Error(val t: Throwable) : LanguageViewEvent()
    data class Retry(val answered: Int, val notAnswered: Int) : LanguageViewEvent()
}