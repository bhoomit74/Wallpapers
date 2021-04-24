package com.bhoomit.wallpapers.retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitService{

    private var apiService : ApiServices? = null

    private fun getRetrofit() : Retrofit {
        val client =OkHttpClient.Builder()

        client.addInterceptor { chain: Interceptor.Chain ->
            val request =chain.request().newBuilder().addHeader("Content-Type","application/json").build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client.build()).build()

    }

    fun getRetrofitService(): ApiServices {
        return if (apiService == null) {
            apiService = getRetrofit().create(ApiServices::class.java)
            apiService!!
        } else{
            apiService!!
        }
    }
}