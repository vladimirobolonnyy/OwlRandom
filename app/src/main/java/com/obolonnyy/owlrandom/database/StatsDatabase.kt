package com.obolonnyy.owlrandom.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.obolonnyy.owlrandom.app.MainApplication

@Database(entities = [TimeStatsEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StatsDatabase : RoomDatabase() {
    abstract fun statsDao(): StatsDao
}

val STATS_DATABASE = Room.databaseBuilder(
    MainApplication.context,
    StatsDatabase::class.java, "stats_database"
).build()