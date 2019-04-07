package com.sdv.niceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.database.ArticleDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookmarkViewModel(private val articleDao: ArticleDao) : AbstractCompositeViewModel() {

    private val mBookmarkLiveData: MutableLiveData<List<Article>> = MutableLiveData()
    val bookmarkLiveData: LiveData<List<Article>> get() = mBookmarkLiveData

    fun loadBookmarks() {
        val disposable = articleDao
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { articleList ->
                mBookmarkLiveData.value = articleList
            }
        addDisposable(disposable)
    }
}