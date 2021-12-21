package com.sung.guardiannews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.SectionNewsItemBinding
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.view.GuardianArticleCallback
import com.sung.guardiannews.view.helpers.ArticleComparator

class GuardianSectionNewsItemsPagingDataAdapter(val callback: GuardianArticleCallback) :
    PagingDataAdapter<Article, GuardianSectionNewsItemsPagingDataAdapter.SectionNewsItemsViewHolder>(
        ArticleComparator
    ) {

    override fun onBindViewHolder(holder: SectionNewsItemsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionNewsItemsViewHolder {
        return SectionNewsItemsViewHolder(
            SectionNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            callback
        )
    }

    class SectionNewsItemsViewHolder(
        private val binding: SectionNewsItemBinding,
        private val callback: GuardianArticleCallback
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.model = item
            binding.callback = callback
            binding.executePendingBindings()
        }
    }
}


