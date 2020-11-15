package com.obolonnyy.owlrandom.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> Fragment.viewModels(noinline creator: (() -> T)? = null): Lazy<T> = lazy {
    if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModels(noinline creator: (() -> T)? = null): Lazy<T> = lazy {
    val activity = this.activity ?: error("empty activity")
    if (creator == null) {
        ViewModelProvider(activity).get(T::class.java)
    } else {
        ViewModelProvider(activity, BaseViewModelFactory(creator)).get(T::class.java)
    }
}


inline fun <reified T : ViewModel> FragmentActivity.viewModels(noinline creator: (() -> T)? = null): Lazy<T> = lazy {
    if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory<T>(private val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator.invoke() as T
    }
}