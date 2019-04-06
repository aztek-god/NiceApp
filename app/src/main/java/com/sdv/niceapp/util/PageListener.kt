package com.sdv.niceapp.util

import androidx.recyclerview.widget.RecyclerView
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.recyclerview.widget.LinearLayoutManager


class PageListener(
    private val layoutManager: LinearLayoutManager,
    private val isLoading: () -> Boolean,
    private val loadMoreItems: () -> Unit
) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }
}