package com.sung.guardiannews.view.helpers

import androidx.recyclerview.widget.DiffUtil
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section


object ArticleComparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem.apiUrl == newItem.apiUrl

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem
}

object SectionComparator : DiffUtil.ItemCallback<Section>() {
    override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean =
        oldItem.sectionName == newItem.sectionName

    override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean =
        oldItem == newItem
}