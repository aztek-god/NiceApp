package com.sdv.niceapp.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.data.TopHeadlinesService
import com.sdv.niceapp.database.ArticleDao
import com.sdv.niceapp.util.Result
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val INIT_PAGE_INDEX = 1
private const val STANDARD_PAGE_SIZE = 10

class ArticleViewModel(
    private val topHeadlinesService: TopHeadlinesService,
    private val articleDao: ArticleDao
) : AbstractCompositeViewModel() {

    private var currentPage = INIT_PAGE_INDEX

    val isLoading: ObservableBoolean = ObservableBoolean()

    private val mArticleLiveData: MutableLiveData<Result<List<Article>>> = MutableLiveData()
    val articleLiveData: LiveData<Result<List<Article>>> get() = mArticleLiveData

    fun loadTopHeadlineNews(isUpdate: Boolean = false) {

        if (isUpdate) {
            currentPage = INIT_PAGE_INDEX
        }

        val disposable =
            topHeadlinesService.getTopHeadlines(country = "us", pageSize = STANDARD_PAGE_SIZE, page = currentPage)
                .map { response ->
                    val articles = response.articles
                    val result = when {
                        articles.isEmpty() -> {
                            Result.createEmpty()
                        }
                        isUpdate -> {
                            Result.createUpdate(articles)
                        }
                        else -> {
                            Result.createData(articles)
                        }
                    }

                    result
                }
                .doOnSubscribe {
                    isLoading.set(true)
                }
                .doOnComplete {
                    isLoading.set(false)
                }
                .doOnError { currentPage = INIT_PAGE_INDEX }
                .doOnNext { currentPage++ }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response: Result<List<Article>> ->
                    mArticleLiveData.value = response
                }, { error ->
                    mArticleLiveData.value = Result.createError(error)
                })

        addDisposable(disposable)
    }

    fun addToFavorites(article: Article) {
        val disposable = Completable.fromAction {
            articleDao.insert(article)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()

        addDisposable(disposable)
    }

    fun removeFromFavorites(article: Article) {
        val disposable = Completable.fromAction {
            articleDao.delete(article)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()

        addDisposable(disposable)
    }
}