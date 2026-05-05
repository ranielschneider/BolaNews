package com.ranielschneider.bolanews.api

import com.ranielschneider.bolanews.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q")        query: String,
        @Query("language") language: String = "pt",
        @Query("sortBy")   sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey")   apiKey: String
    ): NewsResponse
}