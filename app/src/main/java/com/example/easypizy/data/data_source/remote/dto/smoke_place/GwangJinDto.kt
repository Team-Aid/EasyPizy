package com.example.easypizy.data.data_source.remote.dto.smoke_place

import com.google.gson.annotations.SerializedName

data class GwangJinDto(
    // 결과코드 ex) 00
    @SerializedName("resultCode")
    val resultCode: String,

    // 결과메시지 ex) OK
    @SerializedName("resultMsg")
    val resultMsg: String,

    // 흡연구역 아이디 ex) 군자동-02-01-020
    @SerializedName("id")
    val id: String?,

    // 흡연구역명 ex) 세종대학교 학생회관
    @SerializedName("area_nm")
    val areaName: String?,

    //흡연구역범위상세 ex) 학생회관
    @SerializedName("area_desc")
    val areaDescription: String?,

    // 시도명 ex) 서울특별시
    @SerializedName("ctprvnnm")
    val cityName: String?,

    // 시군구명 ex) 광진구
    @SerializedName("signgunm")
    val guName: String?,

    // 읍면동명 ex) 군자동
    @SerializedName("emdnm")
    val dongName: String?,

    // 흡연구역구분 ex) 흡연구역
    @SerializedName("area_se")
    val category: String?,

    // 흡연구역면적 ex) 1536.16
    @SerializedName("area_ar")
    val area: Double,

    // 소재지 도로명주소
    @SerializedName("rdnmadr")
    val roadNameAddress: String?,

    // 소재지 지번주소
    @SerializedName("lnmadr")
    val landNumberAddress: String?,

    // 관리기관명 ex) 광진구청
    @SerializedName("inst_nm")
    val institutionName: String?,

    // 위도
    @SerializedName("latitude")
    val latitude: Double,

    // 경도
    @SerializedName("longitude")
    val longitude: Double,

    // 데이터기준일자 ex) 2021-12-21
    @SerializedName("ref_date")
    val lastChangeDate: String?,

    // 시설이미지 ex)https://~~~~~~.jpeg
    @SerializedName("fclty_knd")
    val image: String?,

    // 한 페이지 결과 수
    @SerializedName("numOfRows")
    val numOfRows: String,

    // 페이지 번호
    @SerializedName("pageNo")
    val pageNo: String,

    // 전체 결과 수
    @SerializedName("totalCount")
    val totalCount: String,

    @SerializedName("note")
    val note: Any?
)