package com.example.capston4

import retrofit2.Call
import retrofit2.http.GET

interface SmokeApi {
    @GET("/v3/19636cc1-f10d-45b6-8ea0-8a52a423d117")
    fun getAllSmoke(): Call<SmokeDto>


}