package com.sung.guardiannews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.view.GuardianArticleCallback
import com.sung.guardiannews.view.helpers.ArticleComparator

class GuardianArticleListAdapter(
    private val layoutId: Int,
    private val callback: GuardianArticleCallback
) :
    ListAdapter<Article, GuardianRecyclerViewHolder>(
        ArticleComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianRecyclerViewHolder {
        return GuardianRecyclerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), layoutId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GuardianRecyclerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, callback) }
    }
}

