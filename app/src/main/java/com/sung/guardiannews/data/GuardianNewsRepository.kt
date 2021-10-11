package com.sung.guardiannews.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sung.guardiannews.data.remote.GuardianNewsPagingSource
import com.sung.guardiannews.data.remote.GuardianServiceResponse
import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GuardianNewsRepository @Inject constructor(private val service: GuardianNewsService) {

    suspend fun getSections(): GuardianServiceResponse<Section> {
        return service.getSections()
    }

//    suspend fun getSections(): Flow<List<Section>> {
//        return flow{
//            emit( service.getSections().response.results)
//        }
//    }

    suspend fun getArticles(sectionId: String): GuardianServiceResponseResult<List<Article>> {
        val serviceResponse = service.getArticles(sectionId)
        val articles: List<Article> = serviceResponse.response.results
        for (article in articles) {
            article.mostViewed = serviceResponse.response.mostViewed
        }
        return GuardianServiceResponseResult.success(articles)
    }

    suspend fun getListArticles(sectionId: String): List<Article> {
        return service.getArticles(sectionId).response.results
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