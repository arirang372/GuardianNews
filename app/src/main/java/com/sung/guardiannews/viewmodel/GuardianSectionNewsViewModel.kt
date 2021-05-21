package com.sung.guardiannews.viewmodel

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
    private val respository: GuardianNewsRepository
) : ViewModel() {

    fun getSectionNewsArticle(
        section: Section,
        pageType: String
    ): Flow<PagingData<Article>> {
        return respository.getSectionNewsArticle(section, pageType).cachedIn(viewModelScope)
    }
}