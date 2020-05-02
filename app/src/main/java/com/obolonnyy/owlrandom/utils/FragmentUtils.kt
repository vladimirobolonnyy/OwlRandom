package com.obolonnyy.owlrandom.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.obolonnyy.owlrandom.R

fun Fragment.materialDialog(): MaterialDialog {
    return MaterialDialog(this.requireContext())
}

fun Fragment.sureMaterialDialog(
    positive: (MaterialDialog) -> Unit,
    negative: (MaterialDialog) -> Unit
): MaterialDialog {
    return MaterialDialog(this.requireContext()).apply {
        title(R.string.material_dialog_delete_title)
        positiveButton(res = R.string.delete, click = positive)
        negativeButton(res = R.string.cancel, click = negative)
    }
}

inline fun <T : Fragment> T.args(builder: Bundle.() -> Unit): T {
    arguments = arguments ?: Bundle().apply(builder)
    return this
}

inline fun <reified T, LD : LiveData<T>> Fragment.observe(
    liveData: LD,
    crossinline block: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer<T> { block(it) })
}
