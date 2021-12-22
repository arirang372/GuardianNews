package com.sung.guardiannews.view.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.BR
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.view.GuardianArticleCallback


class GuardianRecyclerViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Article, callback: GuardianArticleCallback) {
        binding.setVariable(BR.model, item)
        binding.setVariable(BR.callback, callback)
        binding.executePendingBindings()
    }
}