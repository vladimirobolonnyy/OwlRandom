package com.orra.core_ui.theme

import androidx.compose.ui.graphics.Color

private val ColorUnspecified = Color(0xFFF800F8)

class TextColors(
    val primary: Color,
    val secondary: Color,
    val disabled: Color,
    val success: Color,
    val error: Color,
)

class BackgroundColors(
    val primary: Color,
    val secondary: Color,
)

class ElementsColors(
    val primary: Color,
    val secondary: Color,
    val disabled: Color,
    val divider: Color,
    val success: Color,
    val error: Color,
)

class StaticColors(
    val red: Color = Color(0xFFFF6E57),
    val green: Color = Color(0xFF47B36E),
    val blue: Color = Color(0xFF5280FC),
    val white: Color = Color(0xFFFFFFFF)
)

class AppColors(
    val isLight: Boolean,
    val text: TextColors,
    val background: BackgroundColors,
    val elements: ElementsColors,
    val ripple: Color,
) {
    val static: StaticColors = StaticColors()
}

val lightColors = AppColors(
    isLight = true,
    text = TextColors(
        primary = Color(0xFF191C1F),
        secondary = Color(0xFF858587),
        disabled = Color(0xFFC7C7C9),
        success = Color(0xFF47B36E),
        error = Color(0xFFFF6E57),
    ),
    background = BackgroundColors(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFFF2F2F5),
    ),
    elements = ElementsColors(
        primary = Color(0xFF3B3B3D),
        secondary = Color(0xFFABABAD),
        disabled = Color(0xFFC7C7C9),
        divider = Color(0xFFF0F0F2),
        success = Color(0xFF47B36E),
        error = Color(0xFFFF6E57),
    ),
    ripple = Color(0xFF808080)
)

val darkColors = AppColors(
    isLight = false,
    text = TextColors(
        primary = Color(0xFFE3E3E5),
        secondary = Color(0xFF848487),
        disabled = Color(0xFFC7C7C9),
        success = Color(0xFF47B36E),
        error = Color(0xFFFF6E57),
    ),
    background = BackgroundColors(
        primary = Color(0xFF000000),
        secondary = Color(0xFF191C1F),
    ),
    elements = ElementsColors(
        primary = Color(0xFFB8B8BB),
        secondary = Color(0xFF5E5E61),
        disabled = Color(0xFF47474A),
        divider = Color(0xFF0F1214),
        success = Color(0xFF47B36E),
        error = Color(0xFFFF6E57),
    ),
    ripple = Color(0xFFDDDDDD)
)
