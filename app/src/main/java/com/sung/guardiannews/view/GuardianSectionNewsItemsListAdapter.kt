package com.sung.guardiannews.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.SectionNewsItemBinding
import com.sung.guardiannews.model.Article

class GuardianSectionNewsItemsListAdapter :
    ListAdapter<Article, SectionNewsListItemsViewHolder>(ARTICLE_COMPARATOR) {

    override fun onBindViewHolder(holder: SectionNewsListItemsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionNewsListItemsViewHolder {
        return SectionNewsListItemsViewHolder(
            SectionNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.apiUrl == newItem.apiUrl

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}

class SectionNewsListItemsViewHolder(
    private val binding: SectionNewsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Article) {
        binding.model = item
        binding.executePendingBindings()
    }
}

