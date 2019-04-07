package com.sdv.niceapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sdv.niceapp.R
import com.sdv.niceapp.adapter.ArticleListAdapter
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.databinding.FragmentHomeBinding
import com.sdv.niceapp.util.*
import com.sdv.niceapp.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArticleFragment : Fragment() {

    private val articleViewModel: ArticleViewModel by viewModel()

    private val articleListAdapter: ArticleListAdapter = ArticleListAdapter(
        favoriteListener = { isFavorite, article ->
            logd()
        },
        shareLinkListener = { shareUrl ->
            logd()
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // artianDataBinding binding = DataBindingUtil.inflate(
        //            inflater, R.layout.martian_data, container, false);
        //    View view = binding.getRoot();
        //    //here data must be an instance of the class MarsDataProvider
        //    binding.setMarsdata(data);

        val dataBinding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        dataBinding.articleViewModel = articleViewModel


        // Inflate the layout for this fragment
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        with(articleRv) {
            adapter = articleListAdapter
            layoutManager = verticalLayoutManager(this@ArticleFragment.context!!)
            addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.recycler_view_margin).toInt()))
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (!recyclerView.canScrollVertically(1) && !articleViewModel.isLoading.get()) {
                            articleViewModel.loadTopHeadlineNews()
                        }
                    }
                }
            )
        }

        with(articleSwipeRefresh) {
            setOnRefreshListener {
                articleViewModel.loadTopHeadlineNews(isUpdate = true)
            }
        }

        articleViewModel.articleLiveData.observe(this, Observer { result: Result<List<Article>> ->
            articleSwipeRefresh.isRefreshing = false
            when (result) {
                is Result.Progress -> {
                    logd()
                }
                is Result.Data -> {
                    articleListAdapter.addArticles(result.data)
                }
                is Result.Error -> {
                    onError(result.e)
                }
                is Result.Empty -> {
                    articleListAdapter.hideLoader()
                }
                is Result.Update -> {
                    articleListAdapter.updateData(result.data)
                }
            }
        })

        articleViewModel.loadTopHeadlineNews()
    }

    private fun onError(throwable: Throwable) {
        when (handleError(throwable)) {
            ErrorCode.NOT_FOUND -> {

            }

            ErrorCode.UNKNOWN -> {

            }
        }
    }
}
