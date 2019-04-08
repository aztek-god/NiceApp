package com.sdv.niceapp.viewmodel

import com.sdv.niceapp.data.Article
import com.sdv.niceapp.database.ArticleDao
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class ArticleDatabaseViewModel(private val articleDao: ArticleDao) : AbstractCompositeViewModel() {
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