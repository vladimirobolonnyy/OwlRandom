package com.orra.core_ui.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orra.core_ui.theme.AppTheme

@Composable
fun TextElement(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        text = text,
        style = AppTheme.styles.BodyPrimary,
        color = AppTheme.colors.text.primary,
    )
}