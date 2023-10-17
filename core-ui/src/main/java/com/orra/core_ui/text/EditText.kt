package com.orra.core_ui.text

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orra.core_ui.theme.AppTheme


@Composable
fun EditText(
    modifier: Modifier = Modifier,
    value: String?,
    label: String? = null,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value.orEmpty(),
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppTheme.colors.text.primary,
            focusedLabelColor = AppTheme.colors.text.secondary,
            focusedIndicatorColor = AppTheme.colors.static.primary,
            unfocusedIndicatorColor = AppTheme.colors.static.primary,
            disabledIndicatorColor = AppTheme.colors.static.primary,
            focusedContainerColor = AppTheme.colors.background.primary,
            unfocusedContainerColor = AppTheme.colors.background.primary,
        ),
        onValueChange = onValueChange,
        label = label?.let {
            {
                Text(
                    text = it,
                    style = AppTheme.styles.LabelPrimary,
                    color = AppTheme.colors.text.secondary,
                )
            }
        }
    )
}