package com.sung.guardiannews.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.databinding.NewsSectionArticlesBinding
import com.sung.guardiannews.model.Section

class GuardianSectionListAdapter :
    ListAdapter<Section, GuardianSectionListAdapter.GuardianSectionViewHolder>(SECTION_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianSectionViewHolder {
        return GuardianSectionViewHolder(
            NewsSectionArticlesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GuardianSectionViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class GuardianSectionViewHolder(private val binding: NewsSectionArticlesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Section) {
            binding.model = item
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