package com.obolonnyy.owlrandom.network

import com.obolonnyy.owlrandom.model.GooglePicture

class GooglePictureDto {

    private val items: List<GooglePictureItemDto>? = null

    fun convert(wordName: String): GooglePicture {

        val result = mutableListOf<String>()
        var i = 0
        while (items != null && i < 10 && result.size < 3) {
            val item = items[i].link.replaceHttp()
            if (item != null) {
                result.add(item)
            }
            i++
        }
        return GooglePicture(
            wordName = wordName,
            picture1Url = result.getOrElse(0) { "" },
            picture2Url = result.getOrElse(1) { "" },
            picture3Url = result.getOrElse(2) { "" }
        )
    }

    private fun String?.replaceHttp(): String? {
        if (this == null) return null
        if (this.toLowerCase().contains("https")) return this
        if (this.contains("http")) return this.replace("http", "https")
        return null
    }

}

class GooglePictureItemDto {
    val link: String? = null
}