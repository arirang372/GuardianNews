package com.sung.guardiannews.view

import com.sung.guardiannews.model.Section

interface GuardianNewsCallback : GuardianArticleCallback {
    fun onGuardianSectionSelected(section: Section)
}