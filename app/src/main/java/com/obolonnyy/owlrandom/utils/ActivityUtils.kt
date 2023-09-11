@file:Suppress("DEPRECATION")

package com.obolonnyy.owlrandom.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.Window
import android.view.WindowInsetsController


fun Activity.tuneForSystemBars() {
    if (Build.VERSION.SDK_INT >= 30) {
        window.setDecorFitsSystemWindows(false)
    } else {
        val flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        window.decorView.systemUiVisibility = flags
    }
}

// статус бар - он свверху
// LightStatusBar - темные иконки на светлом фоне
// DarkStatusBar - светлые иконки на темном фоне
fun Activity.setLightStatusBar() = window.decorView.setLightStatusBar(true, window)
fun Activity.setDarkStatusBar() = window.decorView.setLightStatusBar(false, window)
fun Activity.toggleStatusBar(light: Boolean) =
    if (light) setLightStatusBar() else setDarkStatusBar()

fun Activity.setLightNavigationBar() = window.decorView.setLightNavigationBar(true, window)
fun Activity.setDarkNavigationBar() = window.decorView.setLightNavigationBar(false, window)
fun Activity.toggleNavigationBar(light: Boolean) =
    if (light) setLightNavigationBar() else setDarkNavigationBar()

private fun View.setLightNavigationBar(light: Boolean, window: Window) {
    if (Build.VERSION.SDK_INT >= 31) {
        window.setDecorFitsSystemWindows(false)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    } else {
        val flag = SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        if (light) addSystemUiVisibility(flag) else removeSystemUiVisibility(flag)
    }
}

private fun View.setLightStatusBar(light: Boolean, window: Window) {
    if (Build.VERSION.SDK_INT >= 31) {
        window.setDecorFitsSystemWindows(false)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        val flag = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (light) addSystemUiVisibility(flag) else removeSystemUiVisibility(flag)
    }
}

private fun View.addSystemUiVisibility(flag: Int) {
    systemUiVisibility = systemUiVisibility or flag
}

private fun View.removeSystemUiVisibility(flag: Int) {
    systemUiVisibility = systemUiVisibility and flag.inv()
}