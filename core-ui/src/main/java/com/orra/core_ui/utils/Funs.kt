package com.orra.core_ui.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.modifyIf(condition: Boolean, f: @Composable Modifier.() -> Modifier) = if (condition) {
    this.f()
} else {
    this
}

@Composable
fun <T> Modifier.modifyNonNull(value: T?, f: @Composable Modifier.(T) -> Modifier) =
    if (value != null) {
        this.f(value)
    } else {
        this
    }

fun Modifier.roundCorners(size: Dp) = clip(RoundedCornerShape(size))

fun Modifier.roundBorder(width: Dp, radius: Dp, color: Color): Modifier {
    return border(width = width, color = color, shape = RoundedCornerShape(size = radius))
}


@Composable
fun RowScope.Space(size: Dp) = Spacer(modifier = Modifier.width(size))

@Composable
fun ColumnScope.Space(size: Dp) = Spacer(modifier = Modifier.height(size))

@Composable
fun Circle(modifier: Modifier, color: Color) {
    Canvas(modifier = modifier, onDraw = { drawCircle(color = color) })
}

@Composable
@ReadOnlyComposable
fun Dp.dpToPx() = LocalDensity.current.run { this@dpToPx.toPx() }

@Composable
@ReadOnlyComposable
fun Float.pxToDp() = LocalDensity.current.run { this@pxToDp.toDp() }