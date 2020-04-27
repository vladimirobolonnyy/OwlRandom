package com.obolonnyy.owlrandom.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupEntity(
    val title: String,
    val items: List<String>,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)