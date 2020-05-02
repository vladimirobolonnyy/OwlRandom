package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.model.MyGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//ToDo make stable interface
interface MainRepository {
//    suspend fun getAllGroups(): List<MyGroup>
//    suspend fun getGroup(id: Long): MyGroup?
//    suspend fun saveGroup(group: MyGroup): MyGroup?
//    suspend fun deleteGroup(group: MyGroup)
}

class MainRepositoryImpl(

) : MainRepository {

    private val dao = GROUPS_DATABASE.groupsDao()

    suspend fun getAllGroups(): Flow<List<MyGroup>> {
        return dao.getAllGroups().map { list: List<GroupEntity> ->
            list.map { fromEntity(it) }
        }
    }

    suspend fun getGroup(id: Long): MyGroup? {
        return dao.getGroup(id)?.let { fromEntity(it) }
    }

    suspend fun getFlowGroup(id: Long): Flow<MyGroup> {
        return dao.getFlowGroup(id).map { fromEntity(it) }
    }

    suspend fun saveGroup(group: MyGroup): MyGroup? {
        val result = if (dao.getGroup(group.id) == null) {
            dao.saveGroup(group.toEntity())
        } else {
            dao.saveGroup(group.toEntity(group.id))
        }
        return getGroup(result)
    }

    suspend fun deleteGroup(group: MyGroup) {
        dao.deleteGroup(group.toEntity(group.id))
    }

    private fun fromEntity(ent: GroupEntity): MyGroup {
        return MyGroup(ent.id, ent.title, ent.items)
    }
}