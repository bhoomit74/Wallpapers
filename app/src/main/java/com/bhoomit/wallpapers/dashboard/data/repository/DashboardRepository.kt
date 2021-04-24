package com.bhoomit.wallpapers.dashboard.data.repository

import com.bhoomit.wallpapers.dashboard.data.model.ImageDetail
import com.bhoomit.wallpapers.retrofit.RetrofitService
import com.bhoomit.wallpapers.util.Result
import java.lang.Exception

object DashboardRepository {
    private var apiServices = RetrofitService.getRetrofitService()

    suspend fun getImages(page : Int) : Result<ArrayList<ImageDetail>>{
        return try {
            val response = apiServices.getImageList(page)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                Result.Success(response.body()!!)
            }
            else{
                Result.Error("No Image Found")
            }
        }catch (exception : Exception){
            Result.Exception(exception)
        }
    }
}