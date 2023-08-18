package com.obolonnyy.owlrandom.presentation.details

import androidx.compose.ui.graphics.Color
import com.obolonnyy.owlrandom.model.MyGroup

data class DetailsViewState(
    val group: MyGroup,
    val items: List<DetailsAdapterItem>
) {

    fun changeColor(index: Int, colorRes: Color): DetailsViewState {
        if (index > this.items.size - 1) return this
        val newList = this.items.toMutableList()
        newList[index] = newList[index].copy(bgColor = colorRes)
        return this.copy(items = newList)
    }

    val title get() = group.title
}

data class DetailsAdapterItem(
    val position: Int,
    val text: String = "",
    val bgColor: Color
)