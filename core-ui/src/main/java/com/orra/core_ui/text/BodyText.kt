package com.orra.core_ui.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orra.core_ui.theme.AppTheme

import com.orra.core_ui.utils.elementClickable

@Composable
fun BodyText(
    text: String,
    onClick: (() -> Unit)? = null
) {
    Text(
        text = text,
        style = AppTheme.styles.BodySecondary,
        color = AppTheme.colors.text.primary,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .elementClickable(onClick = onClick)
            .background(AppTheme.colors.background.primary)
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    )
}