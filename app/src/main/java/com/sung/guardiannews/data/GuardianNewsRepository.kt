package com.sung.guardiannews.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sung.guardiannews.data.remote.GuardianNewsPagingSource
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import com.sung.guardiannews.data.remote.GuardianServiceResponse
import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class GuardianNewsRepository @Inject constructor(private val service: GuardianNewsService) {
    private val serviceResponseResult = ConflatedBroadcastChannel<GuardianServiceResponseResult<List<Article>>>()
    private var lastRequestedPage = STARTING_PAGE_INDEX
    private var isRequestInProgress = false
    private var sectionNewsArticles = mutableListOf<Article>()
    suspend fun getSections(): GuardianServiceResponse<Section> {
        return service.getSections()
    }

    suspend fun getArticles(sectionId: String): GuardianServiceResponseResult<List<Article>> {
        return GuardianServiceResponseResult.success(service.getArticles(sectionId).response.results)
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

//    suspend fun getSectionNewsArticle(section: Section, pageType : String) : Flow<GuardianServiceResponseResult<List<Article>>>{
//        lastRequestedPage = 1
//        sectionNewsArticles.clear()
//        requestAndSaveData(section, pageType)
//        return serviceResponseResult.asFlow()
//    }
//
//    private suspend fun requestAndSaveData(section: Section, pageType: String) : Boolean{
//        isRequestInProgress = true
//        var successful = false
//        try {
//            val response = service.getArticles(sectionId = section.sectionName!!, lastRequestedPage, 10, pageType )
//            sectionNewsArticles.addAll(response.response.results)
//            serviceResponseResult.offer(GuardianServiceResponseResult.success(sectionNewsArticles))
//            successful = true
//        }
//        catch (exception : IOException){
//            serviceResponseResult.offer(GuardianServiceResponseResult.error(exception.message!!, emptyList()))
//        }
//        catch (exception : HttpException){
//            serviceResponseResult.offer(GuardianServiceResponseResult.error(exception.message!!, emptyList()))
//        }
//        finally {
//            isRequestInProgress = false
//        }
//        return successful
//    }
//
//    suspend fun requestMoreSectionNewsArticles(section: Section, pageType : String){
//        if(isRequestInProgress) return
//        if(requestAndSaveData(section, pageType)){
//            lastRequestedPage++
//        }
//    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 5
    }
}