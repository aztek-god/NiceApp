package com.sdv.niceapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sdv.niceapp.R
import com.sdv.niceapp.data.Article
import com.sdv.niceapp.databinding.FragmentDetailBinding

class DetailArticleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val article = arguments?.getParcelable<Article>("article")

        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        binding.article = article

        return binding.root
    }
}