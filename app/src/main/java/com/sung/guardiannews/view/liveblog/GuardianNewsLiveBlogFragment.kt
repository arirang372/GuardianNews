package com.sung.guardiannews.view.liveblog

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sung.guardiannews.R
import com.sung.guardiannews.data.remote.Status
import com.sung.guardiannews.databinding.FragmentGuardianLiveblogDashboardBinding
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import com.sung.guardiannews.view.GuardianNewsCallback
import com.sung.guardiannews.view.adapters.GuardianSectionListAdapter
import com.sung.guardiannews.viewmodel.GuardianNewsLiveBlogViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *   This is a liveblog dashboard fragment
 *
 *   @author John Sung
 */
@AndroidEntryPoint
class GuardianNewsLiveBlogFragment : Fragment(), GuardianNewsCallback {
    private val viewModel: GuardianNewsLiveBlogViewModel by viewModels()
    var sections: List<Section>? = listOf()
    private val sectionListAdapter = GuardianSectionListAdapter(this)
    private lateinit var binding: FragmentGuardianLiveblogDashboardBinding

    private fun fetchSection() {
        //TODO::figure out the better solution to handle the loading bar rather than this solution.
        binding.loadingIndicator.visibility = View.VISIBLE
        viewModel.fetchSections("liveblog")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentGuardianLiveblogDashboardBinding.inflate(inflater, container, false).apply {
                this.viewModel = viewModel
            }
        fetchSection()
        setUpToolbar()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_top_menu_items, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ->
                menu?.findItem(R.id.menu_night_mode_system)?.isChecked = true

            AppCompatDelegate.MODE_NIGHT_AUTO ->
                menu?.findItem(R.id.menu_night_mode_auto)?.isChecked = true

            AppCompatDelegate.MODE_NIGHT_YES ->
                menu?.findItem(R.id.menu_night_mode_night)?.isChecked = true

            AppCompatDelegate.MODE_NIGHT_NO ->
                menu?.findItem(R.id.menu_night_mode_day)?.isChecked = true
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_night_mode_system -> setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            R.id.menu_night_mode_day -> setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.menu_night_mode_night -> setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            R.id.menu_night_mode_auto -> setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNightMode(@AppCompatDelegate.NightMode nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        if (Build.VERSION.SDK_INT >= 11) {
            requireActivity().recreate()
        }
    }

    private fun setUpToolbar() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //TODO::try to figure out the better way to change the overflow menu color
        var drawable = binding.toolbar.overflowIcon
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(drawable.mutate(), resources.getColor(getOverflowMenuColor()))
            binding.toolbar.overflowIcon = drawable
        }
        appCompatActivity.setSupportActionBar(binding.toolbar)
    }

    private fun getOverflowMenuColor() =
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) R.color.white else R.color.black

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
        viewModel.getSectionResponseResult().observe(viewLifecycleOwner) {
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
        }
    }

    override fun onGuardianSectionSelected(section: Section) {
        findNavController().navigate(
            GuardianNewsLiveBlogFragmentDirections
                .actionGuardianNewsLiveBlogFragmentToGuardianSectionNewsFragment()
                .setSection(section)
        )
    }

    override fun onGuardianArticleSelected(article: Article) {
        findNavController().navigate(
            GuardianNewsLiveBlogFragmentDirections.actionGuardianLiveBlogFragmentToGuardianArticleFragment()
                .setArticle(article)
        )
    }
}