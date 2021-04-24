package com.bhoomit.wallpapers.retrofit

import com.bhoomit.wallpapers.dashboard.data.model.ImageDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("v2/list")
    suspend fun getImageList(@Query("page") page : Int) : Response<ArrayList<ImageDetail>>
}