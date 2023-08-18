package com.orra.core_presentation.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

inline fun <reified T : ViewModel> FragmentActivity.activityViewModel(noinline creator: (() -> T)? = null): Lazy<T> = lazy {
    if (creator == null) {
        ViewModelProvider(this)[T::class.java]
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator))[T::class.java]
    }
}

inline fun <reified T : ViewModel> Fragment.fragmentViewModel(noinline creator: (() -> T)? = null): Lazy<T> = lazy {
    if (creator == null) {
        ViewModelProvider(this)[T::class.java]
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator))[T::class.java]
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(noinline creator: (() -> T)? = null): Lazy<T> = lazy {
    val activity = activity ?: error("empty activity")
    if (creator == null) {
        ViewModelProvider(activity)[T::class.java]
    } else {
        ViewModelProvider(activity, BaseViewModelFactory(creator))[T::class.java]
    }
}
class BaseViewModelFactory<T>(private val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator.invoke() as T
    }
}


inline fun ViewModel.launch(crossinline block: suspend () -> Unit) = viewModelScope.launch { block.invoke() }
