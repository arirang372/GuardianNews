package com.sung.guardiannews.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sung.guardiannews.data.GuardianNewsRepository
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GuardianSectionNewsViewModel @Inject constructor(
    private val repository: GuardianNewsRepository
) : ViewModel() {
    val dataLoading = ObservableBoolean(false)
    private lateinit var section: Section
    private var pageType: String = ""

    fun getSectionNewsArticle(
        section: Section,
        pageType: String
    ): Flow<PagingData<Article>> {
        return repository.getSectionNewsArticle(section, pageType).cachedIn(viewModelScope)
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 10
    }
}