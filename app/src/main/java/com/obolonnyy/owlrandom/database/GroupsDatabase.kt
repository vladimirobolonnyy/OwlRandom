package com.obolonnyy.owlrandom.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.obolonnyy.owlrandom.app.MainApplication

@Database(entities = [GroupEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GroupsDatabase : RoomDatabase() {
    abstract fun groupsDao(): GroupsDao
}

val GROUPS_DATABASE by lazy {
    Room.databaseBuilder(
        MainApplication.context,
        GroupsDatabase::class.java, "groups_database"
    ).build()
}