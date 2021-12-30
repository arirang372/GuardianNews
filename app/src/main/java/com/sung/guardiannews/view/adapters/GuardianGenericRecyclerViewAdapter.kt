package com.sung.guardiannews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.view.GuardianArticleCallback

open class GuardianGenericRecyclerViewAdapter<T : ViewDataBinding, M>(
    private val layoutResourceId: Int,
    private var dataset: List<M>,
    private val callback: GuardianArticleCallback
) :
    RecyclerView.Adapter<GuardianGenericRecyclerViewHolder<T>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GuardianGenericRecyclerViewHolder<T> {
        return GuardianGenericRecyclerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), layoutResourceId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GuardianGenericRecyclerViewHolder<T>, position: Int) {
        holder.binding.setVariable(BR.model, getItemAt(position))
        holder.binding.setVariable(BR.callback, callback)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = dataset.size
    private fun getItemAt(position: Int) = dataset[position]
}

class GuardianGenericRecyclerViewHolder<T : ViewDataBinding>(val binding: T) :
    RecyclerView.ViewHolder(binding.root)