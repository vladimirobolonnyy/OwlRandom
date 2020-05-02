package com.obolonnyy.owlrandom.presentation.details

import androidx.annotation.ColorRes
import com.obolonnyy.owlrandom.R

data class DetailsAdapterItem(
    val position: Int,
    val text: String = "",
    @ColorRes val bgColor: Int = R.color.white
)