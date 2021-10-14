package com.sung.guardiannews.view

import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section

interface GuardianNewsCallback {
    fun onGuardianSectionSelected(section: Section)
    fun onGuardianArticleSelected(article: Article)
}