package com.sdv.niceapp.koin

import com.sdv.niceapp.viewmodel.ArticleViewModel
import com.sdv.niceapp.viewmodel.BookmarkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ArticleViewModel(get(), get()) }
    viewModel { BookmarkViewModel(get()) }
}