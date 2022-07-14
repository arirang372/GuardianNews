package com.sung.guardiannews.data.local

import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GuardianNewsDatabaseHelper(private val sectionDao: SectionDao) :
    GuardianNewsDatabaseContract {

    override fun getSections(): Flow<List<Section>> = flow {
        emit(sectionDao.getAllSections())
    }

    override suspend fun getArticles(
        sectionId: String,
        articleType: String
    ): GuardianServiceResponseResult<List<Article>> =
        GuardianServiceResponseResult.success(sectionDao.getSectionsBy(sectionId, articleType))
}