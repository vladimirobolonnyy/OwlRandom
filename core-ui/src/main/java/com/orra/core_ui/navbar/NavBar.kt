package com.orra.core_ui.navbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orra.core_ui.R
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.iconClickable


@Composable
fun NavBar(
    title: String = "",
    subtitle: String = "",
    @DrawableRes iconLeft: Int = R.drawable.ic_back_24,
    @DrawableRes iconRight: Int? = null,
    onLeftIconClicked: (() -> Unit)? = null,
    onRightIconClicked: (() -> Unit)? = null,
) {
    NavBarImpl(
        title = title,
        subtitle = subtitle,
        iconLeft = iconLeft,
        iconRight = iconRight,
        onLeftIconClicked = onLeftIconClicked,
        onRightIconClicked = onRightIconClicked,
    )
}

@Composable
internal fun NavBarImpl(
    title: String = "",
    subtitle: String = "",
    @DrawableRes iconLeft: Int? = null,
    @DrawableRes iconRight: Int? = null,
    onLeftIconClicked: (() -> Unit)? = null,
    onRightIconClicked: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(min = 50.dp)
    ) {
        if (iconLeft != null && onLeftIconClicked != null) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterStart)
            ) {
                NavBarIcon(
                    iconId = iconLeft,
                    onIconClicked = onLeftIconClicked
                )
            }
        }
        if (iconRight != null && onRightIconClicked != null) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterEnd)
            ) {
                NavBarIcon(
                    iconId = iconRight,
                    onIconClicked = onRightIconClicked
                )
            }
        }
        Column(modifier = Modifier.align(Alignment.Center)) {
            TextBlock(title, subtitle)
        }
    }
}

@Composable
private fun TextBlock(
    title: String = "",
    subtitle: String = "",
) {
    val hasTitle = title.isNotBlank()
    val hasSubtitle = subtitle.isNotBlank()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 52.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasTitle) {
            Text(
                text = title,
                style = AppTheme.styles.LabelPrimary,
                color = AppTheme.colors.text.primary,
                textAlign = TextAlign.Center,
            )
        }
        if (hasTitle && hasSubtitle) Space(2.dp)
        if (hasSubtitle) {
            Text(
                text = subtitle,
                style = AppTheme.styles.LabelSecondary,
                color = AppTheme.colors.text.secondary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun NavBarIcon(@DrawableRes iconId: Int, onIconClicked: (() -> Unit)) {
    Icon(
        modifier = Modifier
            .padding(4.dp)
            .size(24.dp)
            .iconClickable(onClick = onIconClicked),
        painter = painterResource(id = iconId),
        contentDescription = null,
        tint = AppTheme.colors.elements.primary
    )
}
