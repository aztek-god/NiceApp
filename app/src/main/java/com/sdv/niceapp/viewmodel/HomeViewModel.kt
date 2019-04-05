package com.sdv.niceapp.viewmodel

import android.util.Log
import com.sdv.niceapp.data.Response
import com.sdv.niceapp.data.TopHeadlinesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val topHeadlinesService: TopHeadlinesService) : AbstractCompositeViewModel() {

    fun loadTopHeadlineNews() {
        val disposable = topHeadlinesService
            .getTopHeadlines(country = "us")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response: Response ->
                Log.d("", "response = $response")
            }, { error ->
                Log.d("", "error = $error")
            })

        addDisposable(disposable)
    }
}