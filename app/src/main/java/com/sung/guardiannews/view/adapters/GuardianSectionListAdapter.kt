package com.sung.guardiannews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.NewsSectionArticlesBinding
import com.sung.guardiannews.model.Section
import com.sung.guardiannews.view.GuardianNewsCallback

class GuardianSectionListAdapter(private val callback: GuardianNewsCallback) :
    ListAdapter<Section, GuardianSectionListAdapter.GuardianSectionViewHolder>(SECTION_COMPARATOR) {

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

    companion object {
        private val SECTION_COMPARATOR = object : DiffUtil.ItemCallback<Section>() {
            override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean =
                oldItem.sectionName == newItem.sectionName

            override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean =
                oldItem == newItem
        }
    }
}