package com.sdv.niceapp.koin

import com.sdv.niceapp.viewmodel.ArticleDatabaseViewModel
import com.sdv.niceapp.viewmodel.ArticleViewModel
import com.sdv.niceapp.viewmodel.BookmarkViewModel
import com.sdv.niceapp.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ArticleViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
    viewModel { ArticleDatabaseViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}