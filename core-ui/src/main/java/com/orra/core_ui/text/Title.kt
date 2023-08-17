package com.orra.core_ui.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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


@Composable
fun Title(
    title: String,
    subtitle: String = "",
    textAlign: TextAlign = TextAlign.Start
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppTheme.colors.background.primary)
            .padding(16.dp, 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = title,
            style = AppTheme.styles.TitlePrimary,
            color = AppTheme.colors.text.primary,
            textAlign = textAlign,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
        )
        if (subtitle.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 4.dp, bottom = 8.dp),
                text = subtitle,
                style = AppTheme.styles.BodySecondary,
                color = AppTheme.colors.text.primary,
                textAlign = textAlign,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5,
            )
        }
    }
}