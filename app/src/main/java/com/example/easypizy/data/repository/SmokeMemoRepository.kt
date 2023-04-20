package com.example.easypizy.data.repository

import androidx.annotation.WorkerThread
import com.example.easypizy.data.data_source.local.dao.SmokeMemoDao
import com.example.easypizy.data.data_source.local.entity.SmokeMemoEntity
import kotlinx.coroutines.flow.Flow

class SmokeMemoRepository(private val smokeMemoDao: SmokeMemoDao) {
    var smokeMemos: Flow<List<SmokeMemoEntity>> = smokeMemoDao.getAllSmokeMemoEntities()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSmokeMemoEntity(memo: SmokeMemoEntity) {
        smokeMemoDao.insertSmokeMemoEntity(memo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteSmokeMemoEntities(vararg memo: SmokeMemoEntity) {

    }
}