package com.obolonnyy.owlrandom.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog

fun Fragment.materialDialog() : MaterialDialog {
    return MaterialDialog(this.requireContext())
}

inline fun <T : Fragment> T.args(builder: Bundle.() -> Unit): T {
    arguments = arguments ?: Bundle()
        .apply(builder)
    return this
}

inline fun <reified T, LD : LiveData<T>> Fragment.observe(liveData: LD, crossinline block: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer<T> { block(it) })
}
