package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.model.Group


interface MainRepository {
    suspend fun getAllGroups(): List<Group>
    suspend fun getGroup(id: Long): Group?
    suspend fun saveGroup(group: Group): Group?
    suspend fun deleteGroup(group: Group)
}

class MainRepositoryImpl(

) : MainRepository {

    private val dao = GROUPS_DATABASE.groupsDao()

    override suspend fun getAllGroups(): List<Group> {
        return dao.getAllGroups().map { fromEntity(it) }
    }

    override suspend fun getGroup(id: Long): Group? {
        return getAllGroups().firstOrNull { it.id == id }
    }

    override suspend fun saveGroup(group: Group): Group? {
        val result = if (dao.getGroup(group.id) == null) {
            dao.saveGroup(group.toEntity())
        } else {
            dao.saveGroup(group.toEntity(group.id))
        }
        return getGroup(result)
    }

    override suspend fun deleteGroup(group: Group) {
        dao.deleteGroup(group.toEntity(group.id))
    }

    private fun fromEntity(ent: GroupEntity): Group {
        return Group(ent.id, ent.title, ent.items)
    }
}