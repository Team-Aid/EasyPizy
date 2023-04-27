package com.example.easypizy.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.easypizy.data.data_source.local.dao.SmokeMemoDao
import com.example.easypizy.data.data_source.local.entity.SmokeMemoEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class SmokeMemoRepository(private val smokeMemoDao: SmokeMemoDao) {
    var smokeMemos: Flow<List<SmokeMemoEntity>> = smokeMemoDao.getAllSmokeMemoEntities()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSmokeMemoEntity(memo: SmokeMemoEntity) {
        smokeMemoDao.insertSmokeMemoEntity(memo)
        Log.d(
            "SmokeMemoRepository",
            memo.toString()
        )
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteSmokeMemoEntities(vararg memo: SmokeMemoEntity) {

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateSmokeMemoEntity(memo: SmokeMemoEntity) {
        smokeMemoDao.updateSmokeMemoEntity(memo)
    }

    fun getSmokeMemoEntity(date: LocalDate): SmokeMemoEntity? {
        val memos = smokeMemoDao.getSmokeMemoEntity(date)
        if(memos.isEmpty()){
            return null
        }
        return smokeMemoDao.getSmokeMemoEntity(date)[0]
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllMemoEntities() {
        smokeMemoDao.deleteAllMemoEntities()
    }
}