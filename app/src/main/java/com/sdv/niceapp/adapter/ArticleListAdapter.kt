package com.sdv.niceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdv.diff_util.DiffUtilAdapter
import com.sdv.niceapp.R
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.databinding.ListItemArticleBinding
import com.sdv.niceapp.util.inflate
import java.lang.RuntimeException

private const val ARTICLE_VIEW_TYPE = 100
private const val PROGRESS_VIEW_TYPE = 101

internal class ArticleListAdapter : DiffUtilAdapter<Article, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ARTICLE_VIEW_TYPE -> {
                val articleBinding: ListItemArticleBinding =
                    ListItemArticleBinding.inflate(layoutInflater, parent, false)
                ArticleViewHolder(articleBinding)
            }

            PROGRESS_VIEW_TYPE -> {
                val view = parent.inflate(R.layout.list_item_loading)
                ProgressViewHolder(view)
            }
            else -> {
                throw RuntimeException("Unknown article view type with index = $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                val model = currentData[position]
                holder.bind(model)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentData.size) {
            PROGRESS_VIEW_TYPE
        } else {
            ARTICLE_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    class ArticleViewHolder(private val articleDataBinding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(articleDataBinding.root) {

        fun bind(article: Article) {
            articleDataBinding.article = article
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)
}