package com.sdv.niceapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.data.TopHeadlinesService
import com.sdv.niceapp.util.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val topHeadlinesService: TopHeadlinesService) : AbstractCompositeViewModel() {

    private val mArticleLiveData: MutableLiveData<Result<List<Article>>> = MutableLiveData()
    val articleLiveData: LiveData<Result<List<Article>>> get() = mArticleLiveData

    fun loadTopHeadlineNews() {
        val disposable = topHeadlinesService
            .getTopHeadlines(country = "us")
            .map { it.articles }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response: List<Article> ->
                mArticleLiveData.value = Result.createData(response, false)
            }, { error ->
                mArticleLiveData.value = Result.createError(error)
            })

        addDisposable(disposable)
    }
}