package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.model.MyGroup
import com.obolonnyy.owlrandom.utils.MyResult
import com.obolonnyy.owlrandom.utils.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface MainRepository {
    suspend fun getAllGroups(): Flow<List<MyGroup>>
    suspend fun getGroup(id: Long): MyGroup?
    suspend fun getFlowGroup(id: Long): MyResult<Flow<MyGroup>>
    suspend fun saveGroup(group: MyGroup): MyGroup?
    suspend fun deleteGroup(group: MyGroup)
}

class MainRepositoryImpl(
    database: GroupsDatabase = GROUPS_DATABASE
) : MainRepository {

    private val dao = database.groupsDao()

    override suspend fun getAllGroups(): Flow<List<MyGroup>> {
        return dao.getAllGroups().map { list: List<GroupEntity> ->
            list.map { fromEntity(it) }
        }
    }

    override suspend fun getGroup(id: Long): MyGroup? {
        return dao.getGroup(id)?.let { fromEntity(it) }
    }

    override suspend fun getFlowGroup(id: Long): MyResult<Flow<MyGroup>> = asResult {
        dao.getFlowGroup(id).map { fromEntity(it) }
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