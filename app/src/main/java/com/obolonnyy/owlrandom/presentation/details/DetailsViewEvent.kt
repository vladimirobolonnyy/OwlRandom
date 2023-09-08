package com.obolonnyy.owlrandom.presentation.details

import com.obolonnyy.owlrandom.presentation.details.random.RandomTypes

sealed class DetailsViewEvent {
    class ShowPickDialog(val items: List<RandomTypes>) : DetailsViewEvent()
    class NavigateToEdit(val groupId: Long) : DetailsViewEvent()
    object NavigateBack : DetailsViewEvent()
}