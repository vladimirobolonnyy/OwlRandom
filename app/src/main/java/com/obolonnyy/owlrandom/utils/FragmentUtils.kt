package com.obolonnyy.owlrandom.utils

import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog

fun Fragment.materialDialog() : MaterialDialog {
    return MaterialDialog(this.requireContext())
}