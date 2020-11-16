package com.obolonnyy.owlrandom.model

data class GooglePicture(
    val wordName: String,
    val picture1Url: String = "",
    val picture2Url: String = "",
    val picture3Url: String = "",
) {
    val picture1: String = "${wordName}_picture1"
    val picture2: String = "${wordName}_picture2"
    val picture3: String = "${wordName}_picture3"
}