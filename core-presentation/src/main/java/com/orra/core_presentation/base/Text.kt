package com.orra.core_presentation.base

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import java.io.Serializable

class Text private constructor(@StringRes val resId: Int?, val text: String?) : Serializable {
    constructor(@StringRes resId: Int) : this(resId = resId, text = null)
    constructor(text: String) : this(resId = null, text = text)
}

fun View.getString(@StringRes resId: Int): String = context.getString(resId)
fun View.getTextString(text: Text?) = text?.text ?: text?.resId?.let(::getString).orEmpty()
fun Text.getString(context: Context) = text ?: resId?.let { context.getString(resId) }.orEmpty()

@Composable
@ReadOnlyComposable
fun Text.getString() = text ?: stringResource(resId!!)