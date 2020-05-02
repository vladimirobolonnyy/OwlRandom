package com.obolonnyy.owlrandom.presentation.create

data class CreateDetailsAdapterItem(
    val position: Int,
    val text: String = "",
    val requestFocus: Boolean = false
)