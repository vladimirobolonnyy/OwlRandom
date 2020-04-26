package com.obolonnyy.owlrandom.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateView(context: Context, @LayoutRes resource: Int) {
    LayoutInflater.from(context).inflate(resource, this, true)
}
fun ViewGroup.inflateView(@LayoutRes resource: Int): View {
    return LayoutInflater.from(this.context).inflate(resource, this, false)
}