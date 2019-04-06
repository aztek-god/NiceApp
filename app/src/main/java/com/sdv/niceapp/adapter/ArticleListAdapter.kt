package com.sdv.niceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdv.diff_util.DiffUtilAdapter
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.databinding.ListItemArticleBinding

internal class ArticleListAdapter : DiffUtilAdapter<Article, ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val articleBinding: ListItemArticleBinding = ListItemArticleBinding.inflate(layoutInflater, parent, false)

        return ArticleViewHolder(articleBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val model = currentData[position]
        holder.bind(model)
    }

    class ArticleViewHolder(private val articleDataBinding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(articleDataBinding.root) {

        fun bind(article: Article) {
            articleDataBinding.article = article
        }
    }
}