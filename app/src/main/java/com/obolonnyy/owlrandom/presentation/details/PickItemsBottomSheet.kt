package com.obolonnyy.owlrandom.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.presentation.details.random.RandomTypes
import com.orra.core_presentation.dialog.BaseComposeBottomSheet
import com.orra.core_presentation.utils.FragmentArgumentsDelegate
import com.orra.core_ui.text.TextElement
import com.orra.core_ui.utils.elementClickable

class PickItemsBottomSheet : BaseComposeBottomSheet() {

    companion object {
        fun newInstance(items: List<RandomTypes>, onItemClicked: ((RandomTypes) -> Unit)) =
            PickItemsBottomSheet().apply {
                this.items = items
                this.onItemClicked = onItemClicked
            }
    }

    private var items: List<RandomTypes> by FragmentArgumentsDelegate()
    private var onItemClicked: ((RandomTypes) -> Unit) = {}

    @Composable
    override fun Content() {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            items.forEach {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .elementClickable {
                            onItemClicked.invoke(it)
                            dismiss()
                        }
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .padding(8.dp)
                ) {
                    TextElement(it.text)
                }
            }
        }
    }
}