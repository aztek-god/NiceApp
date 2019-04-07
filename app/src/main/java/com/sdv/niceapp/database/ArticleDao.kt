package com.sdv.niceapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sdv.niceapp.data.Article
import io.reactivex.Flowable

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): Flowable<List<Article>>

    @Insert
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}