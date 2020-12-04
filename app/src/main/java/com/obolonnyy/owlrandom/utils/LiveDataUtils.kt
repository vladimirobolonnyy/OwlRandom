package com.obolonnyy.owlrandom.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

inline fun <X, Y> LiveData<X>.mapDistinct(crossinline transform: (X) -> Y): LiveData<Y> {
    return map(transform).distinctUntilChanged()
}

inline fun <X, Y> LiveData<X>.map(crossinline transform: (X) -> Y): LiveData<Y> =
    Transformations.map(this) { transform(it) }

fun <X> LiveData<X>.distinctUntilChanged(): LiveData<X> =
    Transformations.distinctUntilChanged(this)