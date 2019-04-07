package com.sdv.niceapp.koin

import android.content.Context
import androidx.room.Room
import com.sdv.niceapp.database.Database
import org.koin.dsl.module

val databaseModule = module {
    single {
        val context: Context = get()
        Room.databaseBuilder(
            context,
            Database::class.java, "app_database"
        ).build()
    }

    single {
        val database = get<Database>()
        database.articleDao()
    }
}