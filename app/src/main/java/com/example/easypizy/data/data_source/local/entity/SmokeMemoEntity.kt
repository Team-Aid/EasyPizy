package com.example.easypizy.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

/**
 * 흡연기록(메모) 엔티티 클래스
 * @param id 아이디
 * @param date 날짜
 * @param cigaretteCount 핀 담배 개비 수
 */
@Entity(tableName = "smoke_memo_table")
data class SmokeMemoEntity(
    @PrimaryKey val id: String,
    val date: LocalDate,
    val cigaretteCount: Int
)