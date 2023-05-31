package com.example.capston4

import retrofit2.Call
import retrofit2.http.GET

interface SmokeApi {
    @GET("/v3/7e2b79b9-f8bc-4543-af1a-60ae9c52a33a")
    fun getAllSmoke(): Call<SmokeDto>


}