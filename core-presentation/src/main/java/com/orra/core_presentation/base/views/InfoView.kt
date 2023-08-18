package com.orra.core_presentation.base.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.orra.core_ui.theme.AppTheme
import com.orra.core_presentation.base.Notification
import com.orra.core_presentation.base.getString

@Composable
internal fun InfoView(notification: Notification, isVisible: Boolean) {
    val bgColor = when (notification) {
        is Notification.Error -> AppTheme.colors.static.red
        is Notification.Success -> AppTheme.colors.static.green
        is Notification.Info -> AppTheme.colors.static.blue
    }
    val text = notification.message.getString()

    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(bgColor)
                .padding(16.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = text,
                style = AppTheme.styles.TitleSecondary,
                color = AppTheme.colors.static.white,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5,
            )
        }
    }
}
