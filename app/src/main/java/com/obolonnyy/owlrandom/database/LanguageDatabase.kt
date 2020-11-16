package com.obolonnyy.owlrandom.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.obolonnyy.owlrandom.app.MainApplication

@Database(entities = [GooglePictureEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LanguageDatabase : RoomDatabase() {
    abstract fun languageDao(): LanguageDao
}

val LANGUAGE_DATABASE = Room.databaseBuilder(
    MainApplication.context,
    LanguageDatabase::class.java, "language_database"
).build()