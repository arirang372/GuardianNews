package com.sung.guardiannews.data

import com.sung.guardiannews.BuildConfig
import com.sung.guardiannews.data.local.GuardianNewsDatabase
import com.sung.guardiannews.data.remote.GuardianServiceResponse
import com.sung.guardiannews.model.Section

class GuardianNewsRepository(
    private val database: GuardianNewsDatabase,
    private val guardianNewsService: GuardianNewsService
) {
    suspend fun getSections(): GuardianServiceResponse<Section> {
        return guardianNewsService.getSections(BuildConfig.GUARDIAN_API_KEY)
    }
}