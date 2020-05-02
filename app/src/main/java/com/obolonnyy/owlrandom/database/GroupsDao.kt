package com.obolonnyy.owlrandom.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupsDao {

    @Query("SELECT * FROM GroupEntity")
    fun getAllGroups(): Flow<List<GroupEntity>>

    @Query("SELECT * FROM GroupEntity WHERE id= :id")
    suspend fun getGroup(id: Long): GroupEntity?

    @Query("SELECT * FROM GroupEntity WHERE id= :id")
    fun getFlowGroup(id: Long): Flow<GroupEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGroup(group: GroupEntity): Long

    @Delete
    suspend fun deleteGroup(group: GroupEntity)
}