package com.sung.guardiannews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.NewsSectionArticlesBinding
import com.sung.guardiannews.model.Section
import com.sung.guardiannews.view.GuardianNewsCallback
import com.sung.guardiannews.view.helpers.SectionComparator

class GuardianSectionListAdapter(private val callback: GuardianNewsCallback) :
    ListAdapter<Section, GuardianSectionListAdapter.GuardianSectionViewHolder>(SectionComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianSectionViewHolder {
        return GuardianSectionViewHolder(
            NewsSectionArticlesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), callback
        )
    }

    override fun onBindViewHolder(holder: GuardianSectionViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class GuardianSectionViewHolder(
        private val binding: NewsSectionArticlesBinding,
        private val callback: GuardianNewsCallback
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Section) {
            binding.model = item
            binding.callback = callback
            binding.executePendingBindings()
        }
    }
}