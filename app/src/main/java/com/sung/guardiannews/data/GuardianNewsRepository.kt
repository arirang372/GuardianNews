package com.sung.guardiannews.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sung.guardiannews.BuildConfig
import com.sung.guardiannews.data.local.GuardianNewsDatabase
import com.sung.guardiannews.data.remote.GuardianServiceResponse
import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Content
import com.sung.guardiannews.model.Section

class GuardianNewsRepository(
    private val database: GuardianNewsDatabase,
    private val guardianNewsService: GuardianNewsService
) {
    suspend fun getSections(): GuardianServiceResponse<Section> {
        return guardianNewsService.getSections()
    }

    suspend fun getNewsContent(sectionId : String) : GuardianServiceResponseResult<List<Content>> {
        return GuardianServiceResponseResult.success(guardianNewsService.getNewsContents(sectionId).response.results)
    }
}