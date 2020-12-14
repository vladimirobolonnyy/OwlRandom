package com.obolonnyy.owlrandom.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color

fun Context.getColorCompose(@ColorRes colorRes: Int) = Color(this.getColorCompat(colorRes))