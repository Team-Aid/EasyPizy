package com.example.easypizy.data.data_source.local.dao

import androidx.room.*
import com.example.easypizy.data.data_source.local.entity.SmokeMemoEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

private const val tableName: String = "smoke_memo_table"

@Dao
interface SmokeMemoDao {
    @Query("SELECT * FROM $tableName")
    fun getAllSmokeMemoEntities(): Flow<List<SmokeMemoEntity>>

    @Query("SELECT * FROM $tableName WHERE date = :date")
    fun getSmokeMemoEntity(date: LocalDate): List<SmokeMemoEntity>

    @Query("SELECT COUNT(*) FROM $tableName")
    fun countSmokeMemoEntities(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSmokeMemoEntity(memo: SmokeMemoEntity)

    @Update
    suspend fun updateSmokeMemoEntity(memo: SmokeMemoEntity)

    @Delete
    suspend fun deleteSmokeMemoEntities(vararg memos: SmokeMemoEntity)

    @Query("DELETE FROM $tableName")
    suspend fun deleteAllMemoEntities()
}