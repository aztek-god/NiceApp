package com.sdv.niceapp.ui.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sdv.niceapp.adapter.ArticleListAdapter
import com.sdv.niceapp.util.Result
import com.sdv.niceapp.util.shortToast
import com.sdv.niceapp.util.verticalLayoutManager
import com.sdv.niceapp.viewmodel.ArticleDatabaseViewModel
import com.sdv.niceapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class SearchFragment : Fragment() {

    private val articleDatabaseViewModel: ArticleDatabaseViewModel by viewModel()
    private val searchArticleViewModel: SearchViewModel by viewModel()

    private val articleListAdapter: ArticleListAdapter = ArticleListAdapter(
        favoriteListener = { isFavorite, article ->
            if (isFavorite) {
                articleDatabaseViewModel.addToFavorites(article)
            } else {
                articleDatabaseViewModel.removeFromFavorites(article)
            }
        },
        shareLinkListener = { shareUrl ->
            ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle(getString(com.sdv.niceapp.R.string.action_share_with))
                .setText(shareUrl)
                .startChooser()
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.sdv.niceapp.R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(searchRv) {
            adapter = articleListAdapter
            layoutManager = verticalLayoutManager(this@SearchFragment.context!!)
            setHasFixedSize(true)
        }

        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(searchArticleView, InputMethodManager.SHOW_IMPLICIT)


        RxTextView
            .afterTextChangeEvents(searchArticleView)
            .skipInitialValue()
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe {
                it.view().text
            }

        searchArticleViewModel.articleLiveData.observe(this, Observer { result ->
            when (result) {
                is Result.Data -> {
                    articleListAdapter.addArticles(result.data)
                }
                is Result.Empty -> {
                    articleListAdapter.hideLoader()
                }
                is Result.Error -> {
                    shortToast(context!!, getString(com.sdv.niceapp.R.string.error_network_exception))
                }
            }
        })
    }
}
