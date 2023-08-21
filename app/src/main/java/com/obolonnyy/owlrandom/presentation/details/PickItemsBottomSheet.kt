package com.obolonnyy.owlrandom.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orra.core_presentation.dialog.BaseComposeBottomSheet
import com.orra.core_presentation.utils.FragmentArgumentsDelegate
import com.orra.core_ui.text.BodyText

class PickItemsBottomSheet : BaseComposeBottomSheet() {

    companion object {
        fun newInstance(items: List<String>, onItemClicked: ((String) -> Unit)) =
            PickItemsBottomSheet().apply {
                this.items = items
                this.onItemClicked = onItemClicked
            }
    }

    private var items: List<String> by FragmentArgumentsDelegate()
    private var onItemClicked: ((String) -> Unit) = {}

    @Composable
    override fun Content() {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            items.forEach {
                BodyText(text = it, onClick = {
                    onItemClicked.invoke(it)
                    dismiss()
                })
            }
        }
    }
//
//    @Composable
//    private fun BodyText(
//        text: String,
//        onClick: (() -> Unit)? = null
//    ) {
//        Text(
//            text = text,
//            style = AppTheme.styles.BodySecondary,
//            color = AppTheme.colors.text.primary,
//            textAlign = TextAlign.Start,
//            modifier = Modifier
//                .fillMaxWidth()
//                .elementClickable(onClick = onClick)
//                .background(AppTheme.colors.background.primary)
//                .padding(16.dp, 0.dp, 16.dp, 16.dp)
//        )
//    }
}