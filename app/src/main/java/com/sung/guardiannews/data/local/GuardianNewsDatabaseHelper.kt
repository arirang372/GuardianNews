package com.sung.guardiannews.data.local

import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuardianNewsDatabaseHelper @Inject constructor(private val sectionDao: SectionDao) :
    GuardianNewsDatabaseContract {

    override fun getSections(): Flow<List<Section>> = flow {
        emit(sectionDao.getAllSections())
    }

//    override suspend fun getArticles(
//        sectionId: String,
//        articleType: String
//    ): GuardianServiceResponseResult<List<Article>> =
//        GuardianServiceResponseResult.success(sectionDao.getSectionsBy(sectionId, articleType))

    override fun insertAll(sections: List<Section>): Flow<Unit> = flow {
        sectionDao.insertAll(sections)
        emit(Unit)
    }

    override suspend fun update(section: Section) {
        sectionDao.updateSection(section)
    }
}