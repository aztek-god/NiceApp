package com.sdv.niceapp.koin

import com.sdv.niceapp.data.SearchNewsService
import com.sdv.niceapp.data.TopHeadlinesService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(TopHeadlinesService::class.java)
    }

    single {
        val retrofit = get<Retrofit>()
        retrofit.create(SearchNewsService::class.java)
    }
}
