package com.obolonnyy.owlrandom.repository

import com.obolonnyy.owlrandom.database.GROUPS_DATABASE
import com.obolonnyy.owlrandom.database.GroupEntity
import com.obolonnyy.owlrandom.database.GroupsDatabase
import com.obolonnyy.owlrandom.model.MyGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface MainRepository {
    suspend fun getAllGroups(): Flow<List<MyGroup>>
    suspend fun getGroup(id: Long?): MyGroup?
    suspend fun getFlowGroup(id: Long): Flow<MyGroup?>
    suspend fun saveGroup(group: MyGroup): MyGroup?
    suspend fun deleteGroup(group: MyGroup)
    suspend fun createEmptyGroup(): MyGroup
}

class MainRepositoryImpl(
    database: GroupsDatabase = GROUPS_DATABASE
) : MainRepository {

    private val dao = database.groupsDao()

    override suspend fun getAllGroups(): Flow<List<MyGroup>> {
        return dao.getAllGroups().map { list: List<GroupEntity> ->
            list.map { it.toModel() }
        }
    }

    override suspend fun getGroup(id: Long?): MyGroup? {
        id ?: return null
        return dao.getGroup(id)?.toModel()
    }

    override suspend fun getFlowGroup(id: Long): Flow<MyGroup?> {
        return dao.getFlowGroup(id).map { fromEntity(it) }
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

    override suspend fun createEmptyGroup(): MyGroup {
        val id = dao.saveGroup(GroupEntity("", emptyList()))
        return dao.getGroup(id)!!.toModel()
    }

    private fun fromEntity(ent: GroupEntity?): MyGroup? {
        // objects can be null, if next screen delete it
        ent ?: return null
        return MyGroup(ent.id, ent.title, ent.items)
    }

    private fun GroupEntity.toModel(): MyGroup {
        return MyGroup(this.id, this.title, this.items)
    }
}