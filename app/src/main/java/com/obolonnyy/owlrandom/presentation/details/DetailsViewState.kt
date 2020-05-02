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

enum class RandomTypes(val index: Int, val text: String) {
    RANDOMIZE_ALL(0, "Randomize all"),
    ONE(1, "Pick one"),
    TWO(2, "Pick two"),
    THREE(3, "Pick three"),
    DIVIDE_TWO(4, "Divide in tho teams"),
    DIVIDE_THEE(5, "Divide in three teams");

    companion object {
        fun get(int: Int): RandomTypes? {
            return RandomTypes.values().firstOrNull { it.index == int }
        }
    }
}