package com.obolonnyy.owlrandom.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obolonnyy.owlrandom.model.GooglePicture

@Entity
class GooglePictureEntity(
    @PrimaryKey
    val wordName: String,
    val picture1Url: String,
    val picture2Url: String,
    val picture3Url: String,
    val date: Long
)

fun GooglePicture.toEntity(date: Long): GooglePictureEntity {
    return GooglePictureEntity(
        wordName = wordName,
        picture1Url = picture1Url,
        picture2Url = picture2Url,
        picture3Url = picture3Url,
        date = date
    )
}

fun GooglePictureEntity.toModel(): GooglePicture {
    return GooglePicture(
        wordName = wordName,
        picture1Url = picture1Url,
        picture2Url = picture2Url,
        picture3Url = picture3Url,
    )
}