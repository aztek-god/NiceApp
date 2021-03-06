package com.sdv.niceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sdv.diff_util.DiffUtilAdapter
import com.sdv.niceapp.R
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.databinding.ListItemArticleBinding
import com.sdv.niceapp.util.gone
import com.sdv.niceapp.util.inflate
import com.sdv.niceapp.util.visible

private const val ARTICLE_VIEW_TYPE = 100
private const val PROGRESS_VIEW_TYPE = 101

internal class ArticleListAdapter(
    private val favoriteListener: (isFavorite: Boolean, article: Article) -> Unit,
    private val shareLinkListener: (String) -> Unit
) :
    DiffUtilAdapter<Article, RecyclerView.ViewHolder>() {

    private var progressViewHolder: ProgressViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ARTICLE_VIEW_TYPE -> {
                val articleBinding: ListItemArticleBinding =
                    ListItemArticleBinding.inflate(layoutInflater, parent, false)
                ArticleViewHolder(articleBinding).apply {
                    favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
                        val article = currentData[adapterPosition]
                        favoriteListener(isChecked, article)
                    }
                    shareButton.setOnClickListener {
                        val article = currentData[adapterPosition]
                        shareLinkListener(article.url)
                    }
                    itemView.setOnClickListener {
                        val article = currentData[adapterPosition]
                        val bundle = bundleOf("article" to article)

                        Navigation.findNavController(itemView)
                            .navigate(R.id.action_homeFragment_to_detailArticleFragment, bundle)
                    }
                }


            }

            PROGRESS_VIEW_TYPE -> {
                val view = parent.inflate(R.layout.list_item_loading)
                progressViewHolder = ProgressViewHolder(view)
                progressViewHolder!!
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

    fun addArticles(newArticleList: List<Article>) {
        val oldDataList = currentData
        updateData(oldDataList + newArticleList)
        progressViewHolder?.run {
            itemView.visible()
        }
    }

    fun hideLoader() {
        progressViewHolder?.run {
            itemView.gone()
        }
    }

    class ArticleViewHolder(private val articleDataBinding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(articleDataBinding.root) {

        val favoriteCheckBox: ToggleButton by lazy {
            articleDataBinding.root.findViewById<ToggleButton>(R.id.favorite)
        }

        val shareButton: ImageButton by lazy {
            articleDataBinding.root.findViewById<ImageButton>(R.id.share)
        }

        fun bind(article: Article) {
            articleDataBinding.article = article
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)
}