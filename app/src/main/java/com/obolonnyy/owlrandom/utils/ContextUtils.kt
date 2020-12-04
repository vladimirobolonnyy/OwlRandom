package com.obolonnyy.owlrandom.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 *  Возвращает drawable с наложенным tint заданного цвета.
 *  @param color - int resource color
 *  @param drawable - drawable from resources.
 */
fun Context.getColoredDrawable(@ColorInt color: Int, @DrawableRes drawable: Int): Drawable =
    ContextCompat.getDrawable(this, drawable)
        ?.let(DrawableCompat::wrap)
        ?.let(Drawable::mutate)
        ?.also { DrawableCompat.setTint(it, color) }
        ?: ColorDrawable(Color.TRANSPARENT)

fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.openImage(uri: Uri) {
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        setDataAndType(uri, "image/*")
    }
    startActivity(intent)
}