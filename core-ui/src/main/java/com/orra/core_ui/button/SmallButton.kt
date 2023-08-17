package com.orra.core_ui.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.orra.core_ui.theme.AppTheme


@Composable
fun SmallButton(
    text: String,
    enabled: Boolean = true,
    onClick: (() -> Unit),
) {
    Button(
        colors = colors(),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        content = {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = AppTheme.styles.LabelSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    )
}

@Composable
private fun colors() = ButtonDefaults.buttonColors(
    containerColor = AppTheme.colors.elements.primary,
    contentColor = Color.White,
    disabledContainerColor = AppTheme.colors.elements.disabled,
    disabledContentColor = Color.White,
)
