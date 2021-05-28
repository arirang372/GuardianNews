package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sung.guardiannews.databinding.FragmentGuardianSectionNewsBinding
import com.sung.guardiannews.model.Section
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchSectionNewsArticles()
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


//    private fun searchSectionNewsArticles() {
//        section?.let {
//            viewModel.getSectionNewsArticleResult(it, "article").observe(this, { result ->
//                when (result.status) {
//                    Status.SUCCESS -> {
//                        adapter.submitList(result.data)
//                    }
//                    Status.ERROR -> {
//                        //TODO::error handing...
//                    }
//                }
//            })
//        }
//    }
//
//    private fun setUpScrollListener(){
//        val layoutManager = binding.sectionNewsItemsRecyclerView.layoutManager as LinearLayoutManager
//        binding.sectionNewsItemsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val totalItemCount = layoutManager.itemCount
//                val visibleItemCount = layoutManager.childCount
//                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
//
//                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
//            }
//        })
//    }
}