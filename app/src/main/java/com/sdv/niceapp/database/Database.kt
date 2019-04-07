package com.sdv.niceapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdv.niceapp.data.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}