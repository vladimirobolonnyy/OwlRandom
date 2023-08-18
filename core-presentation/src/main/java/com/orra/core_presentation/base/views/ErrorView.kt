package com.orra.core_presentation.base.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.elementClickable
import com.orra.core_presentation.base.ErrorAction
import com.orra.core_ui.R

@Composable
internal fun ErrorView(
    errorActions: Set<ErrorAction>,
    onRetryClicked: () -> Unit
) {
    val isVisible = errorActions.isNotEmpty()

    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(AppTheme.colors.static.red)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f, true)
                    .wrapContentHeight(),
                text = stringResource(id = R.string.common_presentation_error),
                style = AppTheme.styles.BodySecondary,
                color = AppTheme.colors.text.primary,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .elementClickable(onClick = onRetryClicked)
                    .border(1.dp, AppTheme.colors.elements.primary, RoundedCornerShape(4.dp))
                    .padding(horizontal = 2.dp),
                text = stringResource(id = R.string.common_presentation_error_repeat),
                style = AppTheme.styles.BodySecondary,
                color = AppTheme.colors.text.primary,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}
