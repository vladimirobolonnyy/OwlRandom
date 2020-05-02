package com.obolonnyy.owlrandom.presentation.create

import com.obolonnyy.owlrandom.model.MyGroup

sealed class CreateDetailsViewState {

    object Empty : CreateDetailsViewState()

    data class Loaded(
        val title: String = "",
        val list: MutableList<CreateDetailsAdapterItem> = mutableListOf()
    ) : CreateDetailsViewState() {

        val deleteBtnIsVisible
        get() = list.isNotEmpty() && list.first().text.isNotBlank()

        constructor(group: MyGroup) : this(group.title, group.items.mapTo().toMutableList())

        fun toGroup(groupId: Long): MyGroup {
            return MyGroup(
                id = groupId,
                title = this.title,
                items = this.list.filter { it.text.isNotBlank() }.map { it.text }
            )
        }
    }
}

private fun List<String>.mapTo(): List<CreateDetailsAdapterItem> {
    return this.mapIndexed { i, s -> CreateDetailsAdapterItem(i, s, false) }
}

sealed class CreateDetailsViewEvent {
    object NavigateToMain : CreateDetailsViewEvent()
}