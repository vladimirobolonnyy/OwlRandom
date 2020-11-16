package com.obolonnyy.owlrandom.network

import com.obolonnyy.owlrandom.model.GooglePicture

class GooglePictureDto {

    private val items: List<GooglePictureItemDto>? = null

    fun convert(wordName: String): GooglePicture {
        return GooglePicture(
            wordName = wordName,
            picture1Url = items?.getOrNull(0)?.link.orEmpty(),
            picture2Url = items?.getOrNull(1)?.link.orEmpty(),
            picture3Url = items?.getOrNull(2)?.link.orEmpty()
        )
    }

}

class GooglePictureItemDto {
    val link: String? = null
}