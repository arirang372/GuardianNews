package com.sung.guardiannews.data

import com.sung.guardiannews.data.remote.GuardianNewsApiContract
import com.sung.guardiannews.model.Section
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuardianNewsRepository @Inject constructor(
    private val apiHelper: GuardianNewsApiContract
) {

    fun getSections() = apiHelper.getSections()

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