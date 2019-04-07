package com.sdv.niceapp.koin

import com.google.gson.GsonBuilder
import com.sdv.niceapp.BuildConfig
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://newsapi.org/v2/"

val retrofitModule = module {

    single {
        GsonBuilder()
            .create()
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.NewsApiKey)
                    .build()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }


}