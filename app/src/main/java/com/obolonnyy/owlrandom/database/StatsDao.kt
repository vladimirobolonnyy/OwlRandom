package com.obolonnyy.owlrandom.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatsDao {

    @Query("SELECT * FROM TimeStatsEntity")
    suspend fun getAll(): List<TimeStatsEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStats(stats: TimeStatsEntity)
}