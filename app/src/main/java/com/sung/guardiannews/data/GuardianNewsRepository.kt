package com.sung.guardiannews.data

import com.sung.guardiannews.data.remote.GuardianServiceResponse
import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import javax.inject.Inject

class GuardianNewsRepository @Inject constructor(private val guardianNewsService: GuardianNewsService) {
    suspend fun getSections(): GuardianServiceResponse<Section> {
        return guardianNewsService.getSections()
    }

    suspend fun getArticles(sectionId: String): GuardianServiceResponseResult<List<Article>> {
        return GuardianServiceResponseResult.success(guardianNewsService.getArticles(sectionId).response.results)
    }
}