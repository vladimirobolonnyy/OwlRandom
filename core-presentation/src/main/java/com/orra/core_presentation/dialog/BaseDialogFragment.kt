package com.orra.core_presentation.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.orra.core_presentation.R
import com.orra.core_presentation.utils.FragmentArgumentsDelegate
import com.orra.core_presentation.utils.className
import com.orra.core_presentation.utils.setThemedContent
import com.orra.core_ui.text.BodyText
import com.orra.core_ui.text.TitleSecondary
import com.orra.core_ui.theme.AppTheme
import com.orra.core_ui.utils.elementClickable


class BaseDialogFragment : DialogFragment(R.layout.fragment_basic_dialog) {

    companion object {
        fun show(
            fragmentManager: FragmentManager?,
            title: String = "",
            content: String = "",
            positiveActionTitle: String = "",
            negativeActionTitle: String = "",
            onPositiveAction: (() -> Unit)? = null,
            onNegativeAction: (() -> Unit)? = null,
        ) {
            fragmentManager ?: return
            val fragment = BaseDialogFragment().apply {
                this.title = title
                this.content = content
                this.positiveActionTitle = positiveActionTitle
                this.negativeActionTitle = negativeActionTitle
                this.onPositiveAction = onPositiveAction
                this.onNegativeAction = onNegativeAction
            }
            fragment.show(fragmentManager, className())
        }
    }

    private var title: String by FragmentArgumentsDelegate()
    private var content: String by FragmentArgumentsDelegate()
    private var positiveActionTitle: String by FragmentArgumentsDelegate()
    private var negativeActionTitle: String by FragmentArgumentsDelegate()
    private var onPositiveAction: (() -> Unit)? = null
    private var onNegativeAction: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.Redesign_Dialog)
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments ?: return
        view.findViewById<ComposeView>(R.id.composeContainer)?.setThemedContent {
            Render()
        }
    }

    @Composable
    private fun Render() {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colors.background.primary)
        ) {
            TitleSecondary(title = title, textAlign = TextAlign.Center)
            BodyText(text = content, textAlign = TextAlign.Center)
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(AppTheme.colors.elements.divider)
            )
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                if (negativeActionTitle.isNotEmpty()) {
                    SmallButton(
                        text = negativeActionTitle,
                        fontWeight = FontWeight.W400,
                        onClick = {
                            onNegativeAction?.invoke()
                            dismiss()
                        })
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(AppTheme.colors.elements.divider)
                )
                if (positiveActionTitle.isNotEmpty()) {
                    SmallButton(
                        text = positiveActionTitle,
                        fontWeight = FontWeight.W600,
                        onClick = {
                            onPositiveAction?.invoke()
                            dismiss()
                        })
                }
            }
        }
    }

    @Composable
    private fun RowScope.SmallButton(
        text: String,
        fontWeight: FontWeight,
        onClick: (() -> Unit),
    ) {
        val bgColor = Color.Transparent
        Text(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(bgColor)
                .elementClickable(onClick = onClick)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            text = text,
            fontWeight = fontWeight,
            color = AppTheme.colors.static.blue,
            textAlign = TextAlign.Center,
            style = AppTheme.styles.LabelPrimary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}