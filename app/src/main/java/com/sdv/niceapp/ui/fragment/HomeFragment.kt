package com.sdv.niceapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdv.niceapp.R
import com.sdv.niceapp.adapter.ArticleListAdapter
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.util.*
import com.sdv.niceapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private val articleListAdapter: ArticleListAdapter = ArticleListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        with(articleRv) {
            adapter = articleListAdapter
            layoutManager = verticalLayoutManager(this@HomeFragment.context!!)
            addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.recycler_view_margin).toInt()))
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (!recyclerView.canScrollVertically(1) && !homeViewModel.isLoading.get()) {
                            homeViewModel.loadTopHeadlineNews()
                        }
                    }
                }
            )
        }

        homeViewModel.articleLiveData.observe(this, Observer { result: Result<List<Article>> ->
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
            }
        })

        homeViewModel.loadTopHeadlineNews()
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
