package com.obolonnyy.owlrandom.create

import com.obolonnyy.owlrandom.model.Group


data class CreateDetailsViewState(
    val group: Group,
    val list: List<CreateDetailsAdapterItem> = group.items.mapTo()
) {
    val title: String = group.title


}

private fun List<String>.mapTo(): List<CreateDetailsAdapterItem> {
    return this.mapIndexed { i, s -> CreateDetailsAdapterItem(i, s, false) }
}