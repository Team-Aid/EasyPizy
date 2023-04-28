package com.example.easypizy.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 흡연장소 엔티티 클래스
 * @param id 아이디
 * @param areaName 흡연구역 이름
 * @param roadNameAddress 도로명주소
 * @param landNumberAddress 지번주소
 * @param category 흡연장소 분류
 * @param latitude 위도
 * @param longitude 경도
 */
@Entity(tableName = "smoke_place_table")
data class SmokePlaceEntity(
    @PrimaryKey val id: String,
    val areaName: String,
    val roadNameAddress: String,
    val landNumberAddress: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
)