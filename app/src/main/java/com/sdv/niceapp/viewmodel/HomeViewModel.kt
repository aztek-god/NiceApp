package com.sdv.niceapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.data.TopHeadlinesService
import com.sdv.niceapp.util.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val INIT_PAGE_INDEX = 1
private const val STANDARD_PAGE_SIZE = 10

class HomeViewModel(private val topHeadlinesService: TopHeadlinesService) : AbstractCompositeViewModel() {

    private var currentPage = INIT_PAGE_INDEX

    private val mArticleLiveData: MutableLiveData<Result<List<Article>>> = MutableLiveData()
    val articleLiveData: LiveData<Result<List<Article>>> get() = mArticleLiveData

    fun loadTopHeadlineNews() {
        val disposable = topHeadlinesService
            .getTopHeadlines(country = "us", page = 4)
            .map { response ->
                val result = if (response.articles.isEmpty()) {
                    Result.createEmpty<List<Article>>()
                } else {
                    Result.createData(response.articles)
                }
                ArticleResult(response.totalResults, result)
            }
            .doOnError { currentPage = INIT_PAGE_INDEX }
            .doOnNext { currentPage++ }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response: ArticleResult ->
                mArticleLiveData.value = response.result
            }, { error ->
                mArticleLiveData.value = Result.createError(error)
            })

        addDisposable(disposable)
    }

    data class ArticleResult(val totalAmount: Int, val result: Result<List<Article>>)
}