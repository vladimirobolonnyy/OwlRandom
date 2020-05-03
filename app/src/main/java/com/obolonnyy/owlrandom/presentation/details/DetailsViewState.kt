package com.obolonnyy.owlrandom.presentation.details

import androidx.annotation.ColorRes
import com.obolonnyy.owlrandom.model.MyGroup

sealed class DetailsViewState {
    object Empty : DetailsViewState()
    object Error : DetailsViewState()

    data class Loaded(
        val group: MyGroup,
        private val items: MutableList<DetailsAdapterItem>
    ) : DetailsViewState() {

        fun changeColor(index: Int, @ColorRes colorRes: Int) : Loaded {
            if (index > items.size - 1) return this
            items[index] = items[index].copy(bgColor = colorRes)
            return this
        }

        val adapterItems: List<DetailsAdapterItem>
            get() = items
    }
}

sealed class DetailsViewEvent {
    data class ShowPickDialog(val items: List<String>) : DetailsViewEvent()
    data class NavigateToEdit(val groupId: Long) : DetailsViewEvent()
}

enum class RandomTypes(val index: Int, val text: String) {
    RANDOMIZE_ALL(0, "Randomize all"),
    ONE(1, "Pick one"),
    TWO(2, "Pick two"),
    THREE(3, "Pick three"),
    FOUR(4, "Pick four"),
    FIVE(5, "Pick five"),
    DIVIDE_TWO(6, "Divide in tho teams"),
    DIVIDE_THEE(7, "Divide in three teams"),
    DIVIDE_FOUR(8, "Divide in four teams"),
    DIVIDE_FIVE(9, "Divide in five teams");

    companion object {
        fun get(int: Int): RandomTypes? {
            return RandomTypes.values().firstOrNull { it.index == int }
        }
    }
}