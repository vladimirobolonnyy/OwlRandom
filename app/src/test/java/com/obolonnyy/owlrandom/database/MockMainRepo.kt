package com.obolonnyy.owlrandom.database

import com.obolonnyy.owlrandom.model.MyGroup
import com.obolonnyy.owlrandom.utils.MyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockMainRepo : MainRepository {

    companion object {
        val group: MyGroup = MyGroup(1, "title", listOf("1", "2", "3"))
    }

    override suspend fun getAllGroups(): Flow<List<MyGroup>> {
        return flow { listOf(group) }
    }

    override suspend fun getGroup(id: Long): MyGroup? {
        return group
    }

    override suspend fun getFlowGroup(id: Long): MyResult<Flow<MyGroup>> {
        return MyResult.Success(flow { group })
    }

    override suspend fun saveGroup(group: MyGroup): MyGroup? {
        return Companion.group
    }

    override suspend fun deleteGroup(group: MyGroup) {
    }

}