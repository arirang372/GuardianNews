package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sung.guardiannews.data.remote.Status
import com.sung.guardiannews.databinding.FragmentGuardianDashboardBinding
import com.sung.guardiannews.model.Section
import com.sung.guardiannews.viewmodel.GuardianDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *   This is a main dashboard fragment
 *
 *   @author John Sung
 */
@AndroidEntryPoint
class GuardianDashboardFragment : Fragment() {
    private var sections: List<Section>? = listOf()
    private val sectionListAdapter = GuardianSectionListAdapter()
    private val articleListAdapter = GuardianArticleListAdapter()
    lateinit var binding: FragmentGuardianDashboardBinding
    private val viewModel: GuardianDashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuardianDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        setUpScrollListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchSections()
    }

    private fun setUpScrollListener(){
        val layoutManager = binding.newsSectionItemsRecyclerView.layoutManager as LinearLayoutManager
        binding.newsSectionItemsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    private fun searchSections() {
        viewModel.getSectionResponseResult().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    sections = it.data
                    //sections = sections?.take(4)
                    binding.newsSectionItemsRecyclerView.adapter = sectionListAdapter.apply {
                        submitList(sections)
                    }

                }
                Status.ERROR -> {
                    //TODO::error handing...

                }
            }
        })
    }
}