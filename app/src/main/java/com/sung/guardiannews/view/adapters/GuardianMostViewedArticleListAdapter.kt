package com.sung.guardiannews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.MostViewedArticleItemBinding
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.view.GuardianArticleCallback
import com.sung.guardiannews.view.helpers.ArticleComparator

class GuardianMostViewedArticleListAdapter(private val callback: GuardianArticleCallback) :
    ListAdapter<Article, GuardianMostViewedArticleListAdapter.GuardianArticleViewHolder>(
        ArticleComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianArticleViewHolder {
        return GuardianArticleViewHolder(
            MostViewedArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GuardianArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, callback) }
    }

    inner class GuardianArticleViewHolder(private val binding: MostViewedArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article, callback: GuardianArticleCallback) {
            binding.model = item
            binding.callback = callback
            binding.executePendingBindings()
        }
    }
}

