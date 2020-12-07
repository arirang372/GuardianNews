package com.sung.guardiannews.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sung.guardiannews.data.GuardianNewsConstants.MAIN_PAGE_TITLES
import com.sung.guardiannews.data.GuardianNewsRepository
import com.sung.guardiannews.data.GuardianNewsService
import com.sung.guardiannews.data.local.GuardianNewsDatabase
import com.sung.guardiannews.data.remote.Status
import com.sung.guardiannews.databinding.FragmentGuardianDashboardBinding
import com.sung.guardiannews.viewmodel.GuardianDashboardViewModel

/**
 *   This is a main dashboard fragment
 *
 *   @author John Sung
 */
class GuardianDashboardFragment : Fragment() {
    private var pagerTitles: MutableList<String>? = null
    private val adapter = GuardianPagerAdapter()
    lateinit var binding: FragmentGuardianDashboardBinding
    private val viewModel: GuardianDashboardViewModel by viewModels {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(GuardianDashboardViewModel::class.java)) {
                    val repository = GuardianNewsRepository(
                        GuardianNewsDatabase.create(activity?.applicationContext!!),
                        GuardianNewsService.create()
                    )
                    return GuardianDashboardViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class : " + modelClass.name)
            }
        }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        viewModel.getSectionResponseResult().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    pagerTitles = it.data?.filter { it -> MAIN_PAGE_TITLES.contains(it.sectionName)
                    }?.map { it -> it.sectionName }?.toMutableList()
                    adapter.setItems(pagerTitles!!)
                }
                Status.ERROR -> {
                    //TODO::error handing...

                }
            }
        })

        viewModel.contentResponseResult.observe(this, {
                val contents = it.data
                Log.d("ContentSize", "${contents?.size}")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuardianDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            //            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }
//
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.selectedTab.value = pagerTitles?.get(position)
            }
        })
        binding.dashboardBottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                //TODO:
            }
            true
        }

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return pagerTitles?.get(position)
    }
}