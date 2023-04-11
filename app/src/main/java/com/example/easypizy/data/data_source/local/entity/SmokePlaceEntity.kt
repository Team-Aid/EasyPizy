package com.example.easypizy.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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