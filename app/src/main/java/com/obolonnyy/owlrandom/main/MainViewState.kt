package com.obolonnyy.owlrandom.main

import com.obolonnyy.owlrandom.model.MyGroup

sealed class MainViewState {
    object Empty : MainViewState()
    data class Loaded(val groups: List<MyGroup>): MainViewState()
}