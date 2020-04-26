package com.obolonnyy.owlrandom.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.viewModels(noinline creator: (() -> T)? = null): Lazy<T> = lazy {
    if (creator == null) {
        ViewModelProviders.of(this).get(T::class.java)
    } else {
        ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

class BaseViewModelFactory<T>(private val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator.invoke() as T
    }
}