package com.obolonnyy.owlrandom.presentation.create

import com.obolonnyy.owlrandom.model.MyGroup


data class CreateGroupViewState(
    val groupId: Long = -1,
    val title: String = "",
    val items: String = "",
) {

    val isEmpty = groupId == -1L

    val deleteBtnIsVisible
        get() = items.isNotEmpty()

    constructor(group: MyGroup) : this(group.id, group.title, group.strItems)
}

sealed class CreateGroupViewEvent {
    object NavigateBack : CreateGroupViewEvent()
    object NavigateToGroups : CreateGroupViewEvent()
    object ShowDeleteDialog : CreateGroupViewEvent()
}