package com.obolonnyy.owlrandom.model

import com.obolonnyy.owlrandom.database.GroupEntity

data class MyGroup(
    val id: Long,
    val title: String,
    val items: List<String>
) {
    val strItems: String = items.joinToString("\n")

    fun toEntity(): GroupEntity {
        return GroupEntity(title, items)
    }

    fun toEntity(id: Long): GroupEntity {
        return GroupEntity(title, items, id)
    }
}

