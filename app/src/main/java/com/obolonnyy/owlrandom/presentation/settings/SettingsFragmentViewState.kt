package com.obolonnyy.owlrandom.presentation.settings

import com.obolonnyy.owlrandom.model.MainTabs

data class SettingsFragmentViewState(
    val loadPictures: Boolean = true,
    val wordsDesiredCount: Int = 0,
    val mainTab: String
) {
    val mainTabValues = MainTabs.values().map { it.str }
}