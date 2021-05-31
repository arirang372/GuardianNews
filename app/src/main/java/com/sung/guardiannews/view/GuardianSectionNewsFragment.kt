package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sung.guardiannews.databinding.FragmentGuardianSectionNewsBinding
import com.sung.guardiannews.model.Section
import com.sung.guardiannews.view.adapters.GuardianSectionNewsItemsPagingDataAdapter
import com.sung.guardiannews.viewmodel.GuardianSectionNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *   This is a section news fragment
 *
 *   @author John Sung
 */
@AndroidEntryPoint
class GuardianSectionNewsFragment : Fragment() {
    private val adapter = GuardianSectionNewsItemsPagingDataAdapter()
    private val viewModel: GuardianSectionNewsViewModel by viewModels()
    private lateinit var binding: FragmentGuardianSectionNewsBinding
    private var section: Section? = null
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuardianSectionNewsBinding.inflate(inflater, container, false)
        binding.sectionNewsItemsRecyclerView.adapter = adapter
        val args: GuardianSectionNewsFragmentArgs by navArgs()
        section = args.section
        binding.model = args.section
        setHasOptionsMenu(true)
        setUpToolbar()
        return binding.root
    }

    private fun setUpToolbar(){
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.setSupportActionBar(binding.toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchSectionNewsArticles()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           android.R.id.home ->
               binding.root.findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchSectionNewsArticles() {
        job?.cancel()
        job = lifecycleScope.launch {
            section?.let { it ->
                viewModel.getSectionNewsArticle(it, "article").collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}