package com.obolonnyy.owlrandom.model

import com.obolonnyy.owlrandom.database.GroupEntity

data class Group(
    val id: Long,
    val title: String,
    val items: List<String>
) {
    fun toEntity(): GroupEntity {
        return GroupEntity(title, items)
    }

    fun toEntity(id: Long): GroupEntity {
        return GroupEntity(title, items, id)
    }
}

