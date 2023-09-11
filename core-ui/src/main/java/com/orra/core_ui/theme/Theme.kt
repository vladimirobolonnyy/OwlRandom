package com.orra.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.setValue

enum class ThemeType {
    SYSTEM, LIGHT, DARK
}

object ThemeState {
    var themeType by mutableStateOf(ThemeType.LIGHT)
}

@Composable
fun PetProjectTheme(
    content: @Composable () -> Unit
) {
    val isDark = when (ThemeState.themeType) {
        ThemeType.SYSTEM -> isSystemInDarkTheme()
        ThemeType.DARK -> true
        ThemeType.LIGHT -> false
    }

    val customAppColors: AppColors = if (isDark) darkColors else lightColors
    MaterialTheme() {
        CompositionLocalProvider(
            LocalAppColors provides customAppColors,
            content = content
        )
    }
}

@Composable
fun ThemeDark(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppColors provides darkColors) {
        content.invoke()
    }
}

@Composable
fun ThemeLight(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppColors provides lightColors) {
        content.invoke()
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val styles: Styles
        get() = Styles

}

private val LocalAppColors = compositionLocalOf(referentialEqualityPolicy()) { lightColors }