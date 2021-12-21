package com.sung.guardiannews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.NewsSectionArticleItemBinding
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.view.GuardianNewsCallback
import com.sung.guardiannews.view.helpers.ArticleComparator

class GuardianArticleListAdapter(private val callback: GuardianNewsCallback) :
    ListAdapter<Article, GuardianArticleListAdapter.GuardianArticleViewHolder>(ArticleComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianArticleViewHolder {
        return GuardianArticleViewHolder(
            NewsSectionArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GuardianArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, callback) }
    }

    class GuardianArticleViewHolder(private val binding: NewsSectionArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article, callback: GuardianNewsCallback) {
            binding.model = item
            binding.callback = callback
            binding.executePendingBindings()
        }
    }
}

