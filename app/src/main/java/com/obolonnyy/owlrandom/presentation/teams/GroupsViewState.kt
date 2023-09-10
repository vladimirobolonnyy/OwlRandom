package com.obolonnyy.owlrandom.presentation.teams

import com.obolonnyy.owlrandom.model.MyGroup

data class GroupsViewState(
    val groups: List<MyGroup> = emptyList()
)

sealed class GroupsViewEvent {
    object GoToCreateItem : GroupsViewEvent()
}