package com.obolonnyy.owlrandom.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.obolonnyy.owlrandom.model.Group

@Dao
interface GroupsDao {

    @Query("SELECT * FROM GroupEntity")
    suspend fun getAllGroups(): List<GroupEntity>

    @Query("SELECT * FROM GroupEntity WHERE id= :id")
    suspend fun getGroup(id: Long): GroupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGroup(group: GroupEntity) : Long

    @Delete
    suspend fun deleteGroup(group: GroupEntity)
}