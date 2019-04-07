package com.sdv.niceapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.sdv.niceapp.R
import com.sdv.niceapp.adapter.BookmarkListAdapter
import com.sdv.niceapp.util.verticalLayoutManager
import com.sdv.niceapp.viewmodel.BookmarkViewModel
import kotlinx.android.synthetic.main.fragment_bookmark.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private val bookmarkAdapter: BookmarkListAdapter = BookmarkListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(bookmarkRv) {
            adapter = bookmarkAdapter
            layoutManager = verticalLayoutManager(this@BookmarkFragment.context!!)
            setHasFixedSize(true)
        }

        bookmarkViewModel.bookmarkLiveData.observe(this, Observer { articleList ->
            bookmarkAdapter.updateData(articleList)
        })

        bookmarkViewModel.loadBookmarks()
    }
}
