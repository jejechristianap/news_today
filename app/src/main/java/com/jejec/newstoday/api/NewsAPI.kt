package com.jejec.newstoday.api

import com.jejec.newstoday.model.NewsResponse
import com.jejec.newstoday.util.Constant
import com.jejec.newstoday.util.Resource
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "id",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constant.API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constant.API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    fun searchForNewsWithRxJava(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constant.API_KEY
    ): Observable<Response<NewsResponse>>


}