package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.model.MyGroup


interface MainRepository {
    suspend fun getAllGroups(): List<MyGroup>
    suspend fun getGroup(id: Long): MyGroup?
    suspend fun saveGroup(group: MyGroup): MyGroup?
    suspend fun deleteGroup(group: MyGroup)
}

class MainRepositoryImpl(

) : MainRepository {

    private val dao = GROUPS_DATABASE.groupsDao()

    override suspend fun getAllGroups(): List<MyGroup> {
        return dao.getAllGroups().map { fromEntity(it) }
    }

    override suspend fun getGroup(id: Long): MyGroup? {
        return getAllGroups().firstOrNull { it.id == id }
    }

    override suspend fun saveGroup(group: MyGroup): MyGroup? {
        val result = if (dao.getGroup(group.id) == null) {
            dao.saveGroup(group.toEntity())
        } else {
            dao.saveGroup(group.toEntity(group.id))
        }
        return getGroup(result)
    }

    override suspend fun deleteGroup(group: MyGroup) {
        dao.deleteGroup(group.toEntity(group.id))
    }

    private fun fromEntity(ent: GroupEntity): MyGroup {
        return MyGroup(ent.id, ent.title, ent.items)
    }
}