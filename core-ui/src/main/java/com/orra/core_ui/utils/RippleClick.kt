package com.orra.core_ui.utils

import android.os.SystemClock
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private val ripple = Color(0xFF808080)

fun Modifier.iconClickable(
    enabled: Boolean = true,
    onClick: (() -> Unit)?
): Modifier = composed {
    throttledClickable(
        indication = rememberRipple(
            bounded = false,
            radius = 20.dp,
            color = ripple
        ),
        enabled = enabled,
        onClick = onClick
    )
}

fun Modifier.elementClickable(
    enabled: Boolean = true,
    throttled: Boolean = true,
    onClick: (() -> Unit)?
): Modifier = composed {
    if (onClick != null) {
        val indication = rememberRipple(
            bounded = true,
            radius = Dp.Unspecified,
            color = ripple
        )
        if (throttled) {
            throttledClickable(
                indication = indication,
                enabled = enabled,
                onClick = onClick
            )
        } else {
            clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = indication,
                enabled = enabled,
                onClick = onClick
            )
        }
    } else {
        this
    }
}

fun Modifier.clearClickable(
    enabled: Boolean = true,
    onClick: (() -> Unit)?
): Modifier {
    return throttledClickable(
        enabled = enabled,
        indication = null,
        onClick = onClick
    )
}

fun throttled(f: () -> Unit): () -> Unit = {
    val now = SystemClock.elapsedRealtime()
    if (now - lastClickTime >= THROTTLE) {
        f.invoke()
        lastClickTime = now
    }
}

private const val THROTTLE = 300L
private var lastClickTime = 0L

private fun Modifier.throttledClickable(
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: (() -> Unit)?
) = composed {
    if (onClick != null) {
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = indication,
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = throttled(onClick)
        )
    } else {
        this
    }
}