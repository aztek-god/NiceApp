package com.sdv.niceapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sdv.diff_util.DiffUtilItem
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
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

@Entity
@Parcelize
data class Source(
    @SerializedName("id")
    val id: String?,
    @ColumnInfo(name = "source_name")
    @SerializedName("name")
    val name: String
) : Parcelable

@Parcelize
@Entity(tableName = "article")
data class Article(
    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author: String?,
    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String,
    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    val publishedAt: String,
    @Embedded
    @SerializedName("source")
    val source: Source,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,
    @ColumnInfo(name = "url_to_image")
    @SerializedName("urlToImage")
    val urlToImage: String
) : DiffUtilItem, Parcelable