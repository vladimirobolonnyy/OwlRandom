package com.orra.core_presentation.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.orra.core_presentation.utils.FragmentArgumentsDelegate
import com.orra.core_presentation.utils.className
import com.orra.core_presentation.utils.setThemedContent
import com.orra.core_ui.R
import com.orra.core_ui.button.SmallButton
import com.orra.core_ui.text.BodyText
import com.orra.core_ui.text.Title
import com.orra.core_ui.theme.AppTheme


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
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colors.background.primary)
        ) {
            Title(title = title)
            BodyText(text = content)
            SmallButton(text = positiveActionTitle, onClick = { onPositiveAction?.invoke() })
            SmallButton(text = negativeActionTitle, onClick = { onNegativeAction?.invoke() })
        }
    }

}