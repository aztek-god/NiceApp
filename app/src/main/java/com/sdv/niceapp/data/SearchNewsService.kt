package com.sdv.niceapp.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchNewsService {
    @GET("everything")
    fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<List<Article>>
}