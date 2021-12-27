package com.sung.guardiannews.view

import com.sung.guardiannews.model.Article

interface GuardianArticleCallback {
    fun onGuardianArticleSelected(article : Article){
        //do nothing by default...
    }
}