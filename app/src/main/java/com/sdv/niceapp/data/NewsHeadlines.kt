package com.sdv.niceapp.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

// Note: you can't mix this param with the sources param.
private const val COUNTRY = "country"
private const val CATEGORY = "category"
private const val SOURCES = "sources"
// Keywords or a phrase to search for.
private const val QUERY = "q"
// The number of results to return per page (request). 20 is the default, 100 is the maximum.
private const val PAGE_SIZE = "pageSize"
private const val PAGE = "page"

// IMPORTANT: Any of these parameters is mandatory: sources, q, language, country, category.
interface TopHeadlinesService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Header("Content-Type") content_type: String = "application/json",
        @Query(COUNTRY) country: String? = null,
        @Query(CATEGORY) category: String? = null,
        @Query(SOURCES) sources: String? = null,
        @Query(QUERY) query: String? = null,
        @Query(PAGE_SIZE) pageSize: Int? = null,
        @Query(PAGE) page: Int? = null
    ): Observable<Response>
}

data class Response(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)

data class Source(
    @SerializedName("id")
    val id: Any,
    @SerializedName("name")
    val name: String
)

data class Article(
    @SerializedName("author")
    val author: Any,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)