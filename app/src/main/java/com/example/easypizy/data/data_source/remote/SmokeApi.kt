package com.example.easypizy.data.data_source.remote

import com.example.easypizy.data.data_source.remote.dto.smoke_place.SmokeDto
import retrofit2.Call
import retrofit2.http.GET

interface SmokeApi {
    @GET("/v3/19636cc1-f10d-45b6-8ea0-8a52a423d117")
    fun getAllSmoke(): Call<SmokeDto>


}