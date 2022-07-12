package com.sung.guardiannews.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sung.guardiannews.data.remote.*
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuardianNewsRepository @Inject constructor(
    private val apiHelper: GuardianNewsApiContract,
    private val service: GuardianNewsService
) {

    fun getSections(): Flow<List<Section>> {
        return apiHelper.getSections()
    }

    suspend fun getArticles(
        sectionId: String,
        articleType: String = ""
    ): Flow<List<Article>> {
        val articles = apiHelper.getArticles(sectionId, articleType).map {
            it.response.results.forEach { article ->
                article.mostViewed = it.response.mostViewed
            }
        }.toList()
//        val articles: List<Article> = serviceResponse.response.results
//        articles.forEach { article ->
//            article.mostViewed = serviceResponse.response.mostViewed
//        }
        return flow {
            GuardianServiceResponseResult.success(articles)
        }
    }

    fun getSectionNewsArticle(
        section: Section,
        pageType: String
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                GuardianNewsPagingSource(service, section, pageType)
            }
        ).flow
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 5
    }
}