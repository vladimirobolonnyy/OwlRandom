package com.obolonnyy.owlrandom.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LanguageDao {

    @Query("SELECT * FROM GooglePictureEntity WHERE wordName= :wordName")
    suspend fun getPictureBy(wordName: String): GooglePictureEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePicture(picture: GooglePictureEntity)
}