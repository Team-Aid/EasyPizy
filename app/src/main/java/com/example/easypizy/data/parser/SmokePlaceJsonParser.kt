package com.example.easypizy.data.parser

import android.util.Log
import com.example.easypizy.data.data_source.local.entity.SmokePlaceEntity
import org.json.JSONObject

object SmokePlaceJsonParser {
    /**
     * @param jsonString 예시: assets.open("yongsan.json").reader().readText()
     * @return json을 변환시키고 나온 SmokePlaceEntity의 List
     */
    fun getSmokePlaceList(jsonString: String):
            List<SmokePlaceEntity> {
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("data")
        val smokePlaces: ArrayList<SmokePlaceEntity> = arrayListOf()
        var guName: String = ""

        if(jsonArray.getJSONObject(1).getString("서울특별시 용산구 설치 위치").isNotBlank()){
            guName = "서울특별시 용산구 설치 위치"
        } else if(jsonArray.getJSONObject(1).getString("서울특별시 성북구 설치 위치").isNotBlank()){
            guName = "서울특별시 성북구 설치 위치"
        } else {
            Log.d("SmokePlaceJsonParser", "guName error!")
        }

        for (i in 0 until jsonArray.length()) {
            smokePlaces.add(
                SmokePlaceEntity(
                    id = jsonArray.getJSONObject(i).getString(guName),
                    areaName = jsonArray.getJSONObject(i).getString(guName),
                    roadNameAddress = "",
                    landNumberAddress = "",
                    category = jsonArray.getJSONObject(i).getString("시설형태"),
                    latitude = jsonArray.getJSONObject(i).getString("위도").toDouble(),
                    longitude = jsonArray.getJSONObject(i).getString("경도").toDouble()
                )
            )
        }
        return smokePlaces.toList()
    }
}