package com.obolonnyy.owlrandom.presentation.create

import com.obolonnyy.owlrandom.model.MyGroup

sealed class CreateDetailsViewState {

    object Empty : CreateDetailsViewState()

    data class Loaded(
        val groupId: Long,
        val title: String = "",
        val list: MutableList<EditDetailsAdapterItem> = mutableListOf()
    ) : CreateDetailsViewState() {

        val deleteBtnIsVisible
            get() = list.isNotEmpty() && list.first().text.isNotBlank()

        constructor(group: MyGroup) : this(group.id, group.title, group.items.mapTo().toMutableList())

    }
}

private fun List<String>.mapTo(): List<EditDetailsAdapterItem> {
    return this.mapIndexed { i, s -> EditDetailsAdapterItem(i, s, false) }
}

sealed class CreateDetailsViewEvent {
    object NavigateToMain : CreateDetailsViewEvent()
}