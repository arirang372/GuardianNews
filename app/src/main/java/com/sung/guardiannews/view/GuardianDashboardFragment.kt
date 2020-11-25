package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sung.guardiannews.R
import com.sung.guardiannews.databinding.FragmentGuardianDashboardBinding
import kotlinx.android.synthetic.main.fragment_guardian_dashboard.*

/**
 *   This is a main dashboard fragment
 *
 *   @author John Sung
 */
class GuardianDashboardFragment : Fragment() {
    private val pagerTitles = mutableListOf("Top news", "Economy", "Social")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGuardianDashboardBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        val adapter = GuardianPagerAdapter()
        adapter.setItems(pagerTitles)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }
//
            override fun onPageSelected(position: Int) {
                binding.dashboardBottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        binding.dashboardBottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_one -> viewPager.currentItem = 0
                R.id.action_two -> viewPager.currentItem = 1
                R.id.action_three -> viewPager.currentItem = 2
            }
            true
        }

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return pagerTitles[position]
    }
}