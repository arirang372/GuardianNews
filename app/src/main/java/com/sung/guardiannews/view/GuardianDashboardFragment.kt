package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
open class GuardianDashboardFragment : Fragment(), GuardianNewsCallback {
    open var sections: List<Section>? = listOf()
    private val sectionListAdapter = GuardianSectionListAdapter(this)
    open lateinit var binding: FragmentGuardianDashboardBinding
    open val viewModel: GuardianDashboardViewModel by viewModels()

    open fun fetchSection() {
        //TODO::figure out the better solution to handle the loading bar rather than this solution.
        binding.loadingIndicator.visibility = View.VISIBLE
        viewModel.fetchSections("article")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuardianDashboardBinding.inflate(inflater, container, false).apply {
            this.viewModel = viewModel
        }
        fetchSection()
        setUpToolbar()
        return binding.root
    }

    private fun setUpToolbar() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        appCompatActivity.setSupportActionBar(binding.toolbar)
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
                    binding.loadingIndicator.visibility = View.GONE
                    sections = it.data
                    viewModel.dashboardTitle.set("${sections?.size} sections")
                    binding.newsSectionItemsRecyclerView.adapter = sectionListAdapter.apply {
                        submitList(sections)
                    }
                }
                Status.ERROR -> {
                    binding.loadingIndicator.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onGuardianSectionSelected(section: Section) {
        findNavController().navigate(
            GuardianDashboardFragmentDirections.actionGuardianDashboardFragmentToGuardianSectionNewsFragment(
                section
            )
        )
    }

    override fun onGuardianArticleSelected(article: Article) {
        findNavController().navigate(
            GuardianDashboardFragmentDirections.actionGuardianDashboardFragmentToGuardianArticleFragment(
                article
            )
        )
    }
}

@AndroidEntryPoint
class GuardianNewsLiveBlogFragment : GuardianDashboardFragment() {
    override fun fetchSection() {
        viewModel.fetchSections("liveblog")
    }

    override fun onGuardianSectionSelected(section: Section) {
        findNavController().navigate(
            GuardianNewsLiveBlogFragmentDirections.actionGuardianNewsLiveBlogFragmentToGuardianSectionNewsFragment(
                section
            )
        )
    }

    override fun onGuardianArticleSelected(article: Article) {
        findNavController().navigate(
            GuardianNewsLiveBlogFragmentDirections.actionGuardianLiveBlogFragmentToGuardianArticleFragment(
                article
            )
        )
    }
}