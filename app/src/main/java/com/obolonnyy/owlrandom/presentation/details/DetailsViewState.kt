package com.obolonnyy.owlrandom.presentation.details

import com.obolonnyy.owlrandom.model.MyGroup

sealed class DetailsViewState {
    object Loading : DetailsViewState()
    object Error : DetailsViewState()

    data class Loaded(
        val group: MyGroup,
        val adapterItems: List<DetailsAdapterItem>
    ) : DetailsViewState()
}