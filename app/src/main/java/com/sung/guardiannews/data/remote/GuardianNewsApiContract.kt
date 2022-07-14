package com.sung.guardiannews.data.remote

import androidx.paging.PagingData
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow

interface GuardianNewsApiContract {

    fun getSections(): Flow<List<Section>>

    suspend fun getArticles(
        sectionId: String,
        articleType: String = ""
    ): GuardianServiceResponseResult<List<Article>>

    fun getSectionNewsArticle(
        section: Section,
        pageType: String
    ): Flow<PagingData<Article>>

}