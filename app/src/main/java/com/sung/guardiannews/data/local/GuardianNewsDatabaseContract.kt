package com.sung.guardiannews.data.local

import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow

interface GuardianNewsDatabaseContract {
    fun getSections(): Flow<List<Section>>

    suspend fun getArticles(
        sectionId: String,
        articleType: String = ""
    ): GuardianServiceResponseResult<List<Article>>
}