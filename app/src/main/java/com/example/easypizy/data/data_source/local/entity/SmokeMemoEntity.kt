package com.example.easypizy.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 흡연기록(메모) 엔티티 클래스
 * @param id 아이디
 * @param title 제목
 * @param content 내용
 */
@Entity(tableName = "smoke_memo_table")
data class SmokeMemoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
)