package com.example.easypizy.data.mapper

import com.example.easypizy.data.data_source.local.entity.SmokePlaceEntity
import com.example.easypizy.data.data_source.remote.dto.smoke_place.GwangJinDto

fun GwangJinDto.toEntity() = SmokePlaceEntity(
    id = id!!,
    areaName = areaName!!,
    roadNameAddress = roadNameAddress!!,
    landNumberAddress = landNumberAddress!!,
    category = category!!,
    latitude = latitude,
    longitude = longitude
)

//fun SmokePlaceEntity.toModel() = SmokePlace(
//    id = id,
//    areaName = areaName,
//    roadNameAddress = roadNameAddress,
//    landNumberAddress = landNumberAddress,
//    category = category,
//    latitude = latitude,
//    longitude = longitude
//)