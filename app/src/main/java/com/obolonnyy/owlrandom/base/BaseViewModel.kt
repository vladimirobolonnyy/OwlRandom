package com.obolonnyy.owlrandom.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun launch(foo: suspend () -> Unit) {
        viewModelScope.launch {
            foo.invoke()
        }
    }

    fun launchIO(foo: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            foo.invoke()
        }
    }

}