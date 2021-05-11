package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private val adapter = GuardianSectionListAdapter()
    lateinit var binding: FragmentGuardianDashboardBinding
    private val viewModel: GuardianDashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuardianDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchSections()
    }

    private fun searchSections() {
        viewModel.getSectionResponseResult().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    sections = it.data
                    sections = sections?.take(4)
                    binding.newsSectionItemsRecyclerView.adapter = adapter.apply {
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