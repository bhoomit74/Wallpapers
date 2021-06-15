package com.bhoomit.wallpapers.retrofit

import com.bhoomit.wallpapers.dashboard.data.model.ImageDetail
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import org.w3c.dom.Text
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    @GET("v2/list")
    suspend fun getImageList(@Query("page") page : Int) : Response<ArrayList<ImageDetail>>
}