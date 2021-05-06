package com.sung.guardiannews.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.NewsSectionArticleItemBinding
import com.sung.guardiannews.model.Article

class GuardianArticleListAdapter :
    ListAdapter<Article, GuardianArticleListAdapter.GuardianArticleViewHolder>(ARTICLE_COMPARATOR) {

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
        getItem(position)?.let { holder.bind(it) }
    }

    class GuardianArticleViewHolder(private val binding: NewsSectionArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.model = item
            binding.executePendingBindings()
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.sectionId == newItem.sectionId

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}

