package com.sdv.niceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdv.diff_util.DiffUtilAdapter
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.databinding.ListItemBookmarkBinding

class BookmarkListAdapter : DiffUtilAdapter<Article, BookmarkListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val articleBinding: ListItemBookmarkBinding =
            ListItemBookmarkBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(articleBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = currentData[position]
        holder.bind(model)
    }

    class ViewHolder(private val binding: ListItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.article = article
        }
    }
}