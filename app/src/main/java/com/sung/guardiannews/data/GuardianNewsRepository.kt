package com.sung.guardiannews.data

import com.sung.guardiannews.data.local.GuardianNewsDatabaseContract
import com.sung.guardiannews.data.remote.GuardianNewsApiContract
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuardianNewsRepository @Inject constructor(
    private val databaseHelper: GuardianNewsDatabaseContract,
    private val apiHelper: GuardianNewsApiContract
) {

    suspend fun updateSection(section: Section) = databaseHelper.update(section)

    fun getSections(): Flow<List<Section>> = databaseHelper.getSections()
        .flatMapMerge { sectionsFromDb ->
            if (sectionsFromDb.isEmpty()) {
                return@flatMapMerge apiHelper.getSections().flatMapConcat { sectionsToInsertInDB ->
                    databaseHelper.insertAll(sectionsToInsertInDB)
                        .flatMapConcat {
                            flow {
                                emit(sectionsToInsertInDB)
                            }
                        }
                }
            } else {
                return@flatMapMerge flow {
                    emit(sectionsFromDb)
                }
            }
        }


    suspend fun getArticles(
        sectionId: String,
        articleType: String = ""
    ) = apiHelper.getArticles(sectionId, articleType)

    fun getSectionNewsArticle(
        section: Section,
        pageType: String
    ) = apiHelper.getSectionNewsArticle(section, pageType)

    companion object {
        const val DEFAULT_PAGE_SIZE = 5
    }
}