package com.orra.core_ui.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.throttled

@Composable
fun BaseButton(
    text: String,
    enabled: Boolean = true,
    bgColor: Color? = null,
    onClick: (() -> Unit),
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = bgColor ?: AppTheme.colors.elements.primary,
        contentColor = Color.White,
        disabledContainerColor = bgColor ?: AppTheme.colors.elements.disabled,
        disabledContentColor = Color.White,
    )
    Button(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        colors = colors,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        onClick = throttled(onClick),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        content = {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = AppTheme.styles.LabelPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    )
}