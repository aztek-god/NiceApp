package com.sdv.niceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.data.SearchNewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.sdv.niceapp.util.Result


private const val INIT_PAGE = 1
private const val PAGE_SIZE = 10

class SearchViewModel(private val searchService: SearchNewsService) : AbstractCompositeViewModel() {

    private var mCurrentQuery: String = ""
    private var mCurrentPage = INIT_PAGE

    private val mArticleLiveData: MutableLiveData<Result<List<Article>>> = MutableLiveData()
    val articleLiveData get() = mArticleLiveData

    // first in query
    fun searchArticle(query: String) {
        mCurrentQuery = query

        val disposable = searchService
            .searchNews(
                query = mCurrentQuery,
                page = mCurrentPage,
                pageSize = PAGE_SIZE
            )
            .map { articleList ->
                val res = when {
                    articleList.isEmpty() -> {
                        Result.createEmpty<List<Article>>()
                    }
                    else -> {
                        Result.createData(articleList)
                    }
                }

                res
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    mArticleLiveData.value = result
                },
                { error ->
                    mArticleLiveData.value = Result.createError(error)
                }
            )

        addDisposable(disposable)
    }

    // for pagination purpose
    fun loadMore() {
        mCurrentPage++
        searchArticle(mCurrentQuery)
    }
}