package com.sung.guardiannews.data.remote

import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow

interface GuardianNewsApiContract {
    fun getSections(): Flow<List<Section>>

    fun getArticles(
        sectionId: String,
        articleType: String = "article" //type = "article or liveblog"
    ): Flow<GuardianServiceResponse<Article>>
}