package com.sung.guardiannews.view.adapters

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sung.guardiannews.view.GuardianPagerAdapter
import java.text.FieldPosition

class BindingAdapters {

    @BindingAdapter("tabItems")
    fun setTabItems(viewPager: ViewPager2, items : MutableList<String>){
        var adapter = GuardianPagerAdapter()
        adapter.setItems(items)
        viewPager.adapter = adapter
    }

}