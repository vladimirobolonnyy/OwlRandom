package com.obolonnyy.owlrandom.model

import android.net.Uri

data class LanguageImages(
    val picture1Uri: Uri,
    val picture2Uri: Uri,
    val picture3Uri: Uri
) {
    val picturesUri = listOf(picture1Uri, picture2Uri, picture3Uri)
}