package com.example.capston4

import retrofit2.Call
import retrofit2.http.GET

interface NoSmokeApi {
    @GET("/v3/510b2b00-9334-4fa1-817d-48e25298fdff")
    fun getNoSmoke(): Call<NoSmokeDto>


}