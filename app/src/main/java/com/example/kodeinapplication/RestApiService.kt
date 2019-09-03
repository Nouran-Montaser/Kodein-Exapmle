package com.example.kodeinapplication

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface RestApiService {

    @GET("rss.xml")
    fun getPopularBlog(): Call<String>
}