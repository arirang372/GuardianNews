package com.sung.guardiannews.data.remote

import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class GuardianNewsApiHelper(private val service: GuardianNewsService) :
    GuardianNewsApiContract {

    override fun getSections(): Flow<List<Section>> = flow {
        emit(service.getSections())
    }.map {
        it.response.results
    }

    override fun getArticles(
        sectionId: String,
        articleType: String
    ): Flow<GuardianServiceResponse<Article>> = flow {
        emit(
            service.getArticles(
                sectionId, 1, 10, articleType, "all", "all", true
            )
        )
    }
}