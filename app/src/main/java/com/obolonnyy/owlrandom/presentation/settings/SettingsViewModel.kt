package com.obolonnyy.owlrandom.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.repository.UserSettings
import com.obolonnyy.owlrandom.repository.UserSettingsImpl
import com.obolonnyy.owlrandom.utils.mapDistinct

class SettingsViewModel(
    private val settingsRepo: UserSettings = UserSettingsImpl(),
) : BaseViewModel() {

    private val _viewState = MutableLiveData<SettingsFragmentViewState>()
    val viewState: LiveData<SettingsFragmentViewState> = _viewState

    val switchEnabled: LiveData<Boolean> = _viewState.mapDistinct { it.loadPictures }
    val wordsDesiredCount: LiveData<Int> = _viewState.mapDistinct { it.wordsDesiredCount }

    init {
        loadStateFromRepo()
    }

    fun onSwitchClicked(newValue: Boolean) {
        settingsRepo.loadPictures = newValue
        loadStateFromRepo()
    }

    fun onDesiredUpdated(newValue: Float) {
        try {
            val i = (newValue * 100).toInt()
            settingsRepo.wordsDesiredCount = i
            loadStateFromRepo()
        } catch (e: NumberFormatException) {
            //ingore
        }
    }

    private fun loadStateFromRepo() {
        _viewState.setValue(
            SettingsFragmentViewState(
                loadPictures = settingsRepo.loadPictures,
                wordsDesiredCount = settingsRepo.wordsDesiredCount
            )
        )
    }

}