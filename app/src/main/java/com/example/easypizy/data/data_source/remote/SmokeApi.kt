package com.example.easypizy.data.data_source.remote

import com.example.easypizy.data.data_source.remote.dto.smoke_place.SmokeDto
import retrofit2.Call
import retrofit2.http.GET

interface SmokeApi {
    @GET("v3/3eb4bdc9-1f03-40d4-8813-2c938581ae60")
    fun getAllSmoke(): Call<SmokeDto>


}