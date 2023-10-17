package com.obolonnyy.owlrandom.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.obolonnyy.owlrandom.presentation.details.random.RandomTypes
import com.orra.core_presentation.dialog.BaseBottomSheet
import com.orra.core_ui.text.Divider
import com.orra.core_ui.text.TextElement
import com.orra.core_ui.utils.Space
import com.orra.core_ui.utils.elementClickable

class PickItemsBottomSheet : BaseBottomSheet() {

    companion object {
        fun newInstance(items: List<RandomTypes>, onItemClicked: ((RandomTypes) -> Unit)) =
            PickItemsBottomSheet().apply {
                this.items = items
                this.onItemClicked = onItemClicked
            }
    }

    private var items: List<RandomTypes>? = emptyList()
    private var onItemClicked: ((RandomTypes) -> Unit) = {}

    @Composable
    override fun SheetContent() {
        Column(modifier = Modifier.wrapContentHeight()) {
            items?.forEach {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .elementClickable {
                            onItemClicked.invoke(it)
                            dismiss()
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    TextElement(it.text)
                    Space(size = 8.dp)
                    Divider()
                }
            }
        }
    }
}