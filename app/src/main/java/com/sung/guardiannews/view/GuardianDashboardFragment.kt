package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sung.guardiannews.data.remote.Status
import com.sung.guardiannews.databinding.FragmentGuardianDashboardBinding
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import com.sung.guardiannews.view.adapters.GuardianSectionListAdapter
import com.sung.guardiannews.viewmodel.GuardianDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *   This is a main dashboard fragment
 *
 *   @author John Sung
 */
@AndroidEntryPoint
class GuardianDashboardFragment : Fragment(), GuardianNewsCallback {
    private var sections: List<Section>? = listOf()
    private val sectionListAdapter = GuardianSectionListAdapter(this)
    private lateinit var binding: FragmentGuardianDashboardBinding
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
        setUpNestedScrollViewScrollChangeListener()
    }

    private fun setUpNestedScrollViewScrollChangeListener() {
        var isToolbarShown = false
        binding.newsSectionScrollview.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                // User scrolled past image to height of toolbar and the title text is
                // underneath the toolbar, so the toolbar should be shown.
                val shouldShowToolbar = scrollY > binding.toolbar.height
                // The new state of the toolbar differs from the previous state; update
                // appbar and toolbar attributes.
                if (isToolbarShown != shouldShowToolbar) {
                    isToolbarShown = shouldShowToolbar
                    // Use shadow animator to add elevation if toolbar is shown
                    binding.appbar.isActivated = shouldShowToolbar
                    // Show the plant name if toolbar is shown
                    binding.toolbarLayout.isTitleEnabled = shouldShowToolbar
                }
            }
        )
    }

    private fun searchSections() {
        viewModel.getSectionResponseResult().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    sections = it.data
                    viewModel.dashboardTitle.set("${sections?.size} sections")
                    binding.newsSectionItemsRecyclerView.adapter = sectionListAdapter.apply {
                        submitList(sections)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onGuardianSectionSelected(section: Section) {
        findNavController().navigate(GuardianDashboardFragmentDirections.actionGuardianDashboardFragmentToGuardianSectionNewsFragment(section))
    }

    override fun onGuardianArticleSelected(article: Article) {
        findNavController().navigate(GuardianDashboardFragmentDirections.actionGuardianDashboardFragmentToGuardianArticleFragment(article))
    }
}