package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sung.guardiannews.databinding.FragmentMostViewedArticleBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *   This is a Fragment that renders the most viewed article
 *
 *   @author John Sung
 */
@AndroidEntryPoint
class GuardianMostViewedArticleFragment : Fragment() {

    private lateinit var binding: FragmentMostViewedArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMostViewedArticleBinding.inflate(inflater, container, false)
        val args: GuardianArticleFragmentArgs by navArgs()
        binding.model = args.article
        setHasOptionsMenu(true)
        setUpToolbar()
        return binding.root
    }

    private fun setUpToolbar() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.setSupportActionBar(binding.toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                binding.root.findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}