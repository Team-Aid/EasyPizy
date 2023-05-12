package com.example.capston4


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoSmokeArea(
    @SerializedName("경도")
    val latitude: Double,
    @SerializedName("서울특별시 설치 위치")
    val areaName: String,
    @SerializedName("설치 주체")
    val a: String,
    @SerializedName("시설 구분")
    val inout: String,
    @SerializedName("시설형태")
    val category: String,
    @SerializedName("위도")
    val longitude: Double,
    @SerializedName("자치구명")
    val b: String
) : Parcelable



