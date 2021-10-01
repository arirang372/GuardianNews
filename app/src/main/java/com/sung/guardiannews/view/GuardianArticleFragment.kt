package com.sung.guardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sung.guardiannews.databinding.FragmentGuardianArticleBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *   This is a Fragment that renders the article
 *
 *   @author John Sung
 */
@AndroidEntryPoint
class GuardianArticleFragment : Fragment() {
    private lateinit var binding: FragmentGuardianArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuardianArticleBinding.inflate(inflater, container, false)
        binding.webview.settings.javaScriptEnabled = true
        val args: GuardianArticleFragmentArgs by navArgs()
        args.article?.let {
            args.article!!.fields?.bodyText?.let { it1 ->
                binding.webview.loadData(
                    it1,
                    "text/html; charset=utf-8",
                    "UTF-8"
                )
            }
        }
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

}