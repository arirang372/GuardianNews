package com.sung.guardiannews.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.R
import java.util.*

class GuardianPagerAdapter : RecyclerView.Adapter<GuardianPagerViewHolder>() {
    private var items: MutableList<String> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianPagerViewHolder {
        return GuardianPagerViewHolder.create(parent)
    }

    private fun getItem(position: Int) = items[position]

    override fun onBindViewHolder(holder: GuardianPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = items.size

    fun setItems(items : MutableList<String>){
        this.items = items
        notifyDataSetChanged()
    }
}


class GuardianPagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val itemName = view.findViewById<TextView>(R.id.itemName)

    fun bind(itemText: String) {
        itemName.text = itemText
    }

    companion object {
        fun create(parent: ViewGroup): GuardianPagerViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
            return GuardianPagerViewHolder(view)
        }
    }
}