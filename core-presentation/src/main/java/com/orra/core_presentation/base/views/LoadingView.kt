package com.orra.core_presentation.base.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orra.core_ui.theme.AppTheme

@Composable
internal fun LoadingView(loading: Boolean) {
    val white50 = Color(0x80FFFFFF)
    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(white50)
                .blur(Dp.Unspecified),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = AppTheme.colors.elements.primary,
                strokeWidth = 3.dp,
            )
        }
    }
}