package com.obolonnyy.owlrandom.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obolonnyy.owlrandom.utils.MyResult
import com.obolonnyy.owlrandom.utils.onFailure
import com.obolonnyy.owlrandom.utils.onSuccess
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

    protected inline fun <T> MyResult<T>.onFailureUI(
        ignoreCancellation: Boolean = true,
        crossinline action: (exception: Throwable) -> Unit,
    ): MyResult<T> {
        return onFailure(viewModelScope, action)
    }

    protected inline fun <T> MyResult<T>.onSuccessUI(
        crossinline action: (value: T) -> Unit,
    ): MyResult<T> = onSuccess(viewModelScope, action)

}