package com.orra.core_presentation.base

import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.orra.core_presentation.utils.NotNullMutableLiveData
import com.orra.core_presentation.utils.SingleLiveEvent
import com.orra.core_ui.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel<E : Any> : ViewModel() {

    protected val _viewEvents = SingleLiveEvent<E>()
    val viewEvents: LiveData<E> = _viewEvents

    private val _notificationLiveData = SingleLiveEvent<Notification>()
    val notificationsEvents: LiveData<Notification> = _notificationLiveData

    protected val mutableErrorState = NotNullMutableLiveData<Set<ErrorAction>>(emptySet())
    val errorState = mutableErrorState.distinctUntilChanged()

    protected val mutableModalLoadingState = NotNullMutableLiveData(false)
    val modalLoadingState = mutableModalLoadingState.distinctUntilChanged()

    protected fun launchWithProgress(block: suspend () -> Unit): Job {
        return launchProgress(true, block)
    }

    protected fun launchIO(block: suspend () -> Unit): Job {
        return launchProgress(false, block)
    }

    private fun launchProgress(loader: Boolean, block: suspend () -> Unit): Job {
        val job = viewModelScope.launch(Dispatchers.IO) {
            toggleLoader(loader)
            block.invoke()
        }
        job.invokeOnCompletion { toggleLoader(false) }
        return job
    }

    protected inline fun <T> Result<T>.onSuccessUI(
        crossinline action: (value: T) -> Unit,
    ): Result<T> = also { viewModelScope.launch(Dispatchers.Main) { onSuccess(action) } }


    protected inline fun <T> Result<T>.onFailureUI(
        crossinline action: (exception: Throwable) -> Unit,
    ): Result<T> {
        val error = exceptionOrNull() ?: return this

        if (error !is CancellationException) {
            logError(error)
            viewModelScope.launch(Dispatchers.Main) {
                onFailure(action)
            }
        }
        return this
    }

    @CallSuper
    open fun onRetryAction(action: ErrorAction) {
        mutableErrorState.value = mutableErrorState.value - action
    }

    @CallSuper
    open fun onRetryActions() {
        val actions = mutableErrorState.value
        actions.forEach { onRetryAction(it) }
    }

    protected fun toggleLoader(visible: Boolean) {
        mutableModalLoadingState.postValue(visible)
    }

    fun logError(error: Throwable, additionalTag: String? = null) {
    }


    protected fun addErrorAction(action: ErrorAction) {
        mutableErrorState.postValue(mutableErrorState.value + action)
    }

    protected fun showSuccessMessage(@StringRes message: Int) =
        Notification.Success(Text(message)).post()

    protected fun showSuccessMessage(message: String) = Notification.Success(Text(message)).post()

    protected fun showInfoMessage(message: String) = Notification.Info(Text(message)).post()

    protected fun showInfoMessage(@StringRes message: Int) = Notification.Info(Text(message)).post()

    protected fun showErrorMessage(@StringRes message: Int = R.string.common_presentation_error) =
        Notification.Error(Text(message)).post()

    protected fun showErrorMessage(message: String) = Notification.Error(Text(message)).post()

    protected fun launchAndForget(block: suspend () -> Unit) =
        launchIO { runCatching { block.invoke() } }

    private fun Notification.post() = _notificationLiveData.postValue(this)

    protected fun E.post(immediately: Boolean = false) {
        if (immediately) {
            _viewEvents.value = this
        } else {
            _viewEvents.postValue(this)
        }
    }
}