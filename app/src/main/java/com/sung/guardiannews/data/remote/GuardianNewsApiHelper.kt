package com.sung.guardiannews.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sung.guardiannews.data.GuardianNewsRepository
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GuardianNewsApiHelper @Inject constructor(private val service: GuardianNewsService) :
    GuardianNewsApiContract {

    override fun getSections(): Flow<List<Section>> = flow {
        emit(service.getSections())
    }.map {
        it.response.results
    }

    override suspend fun getArticles(
        sectionId: String,
        articleType: String
    ): GuardianServiceResponseResult<List<Article>> {
        val serviceResponse = service.getArticles(sectionId, 1, 10, articleType, "all", "all", true)
        val articles: List<Article> = serviceResponse.response.results
        articles.forEach { article ->
            article.mostViewed = serviceResponse.response.mostViewed
        }
        return GuardianServiceResponseResult.success(articles)
    }

    override fun getSectionNewsArticle(
        section: Section,
        pageType: String
    ): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = GuardianNewsRepository.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                GuardianNewsPagingSource(service, section, pageType)
            }
        ).flow

}