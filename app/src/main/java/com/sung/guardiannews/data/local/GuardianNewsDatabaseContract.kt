package com.sung.guardiannews.data.local

import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow

interface GuardianNewsDatabaseContract {
    fun getSections(): Flow<List<Section>>

//    suspend fun getArticles(
//        sectionId: String,
//    ): GuardianServiceResponseResult<List<Article>>

    fun insertAll(sections: List<Section>): Flow<Unit>

    suspend fun update(section: Section)
}