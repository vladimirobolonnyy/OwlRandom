package com.obolonnyy.owlrandom.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*

fun ViewGroup.inflateView(context: Context, @LayoutRes resource: Int) {
    LayoutInflater.from(context).inflate(resource, this, true)
}
fun ViewGroup.inflateView(@LayoutRes resource: Int): View {
    return LayoutInflater.from(this.context).inflate(resource, this, false)
}

fun View.getColor(@ColorRes colorRes: Int): Int {
    return this.context.getColorCompat(colorRes)
}

fun View.setBgColor(@ColorRes colorRes: Int) {
    this.setBackgroundColor(this.context.getColorCompat(colorRes))
}

fun View.setTintBg(@ColorRes colorRes: Int) {
    this.background.setTintList(ContextCompat.getColorStateList(context, colorRes))
}