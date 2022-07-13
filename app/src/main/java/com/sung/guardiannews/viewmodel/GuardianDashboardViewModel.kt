package com.sung.guardiannews.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sung.guardiannews.data.GuardianNewsRepository
import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class GuardianDashboardViewModel @Inject constructor(
    private val repository: GuardianNewsRepository
) : ViewModel() {
    val dataLoading = ObservableBoolean(false)
    val dashboardTitle = ObservableField<String>()
    private val sectionResponseResult =
        MutableLiveData<GuardianServiceResponseResult<List<Section>>>()

    fun fetchSections(articleType: String = "") = viewModelScope.launch {
        if (sectionResponseResult.value?.data?.isNotEmpty() == true) {
            return@launch
        }
        dataLoading.set(true)
        try {
            val elapsed = measureTimeMillis {
                repository.getSections()
                    .flowOn(Dispatchers.Default)
                    .catch { e ->
                        sectionResponseResult.value =
                            GuardianServiceResponseResult.error(e.toString(), null)
                    }
                    .map { processGettingArticles(it, articleType) }
                    .collect {
                        sectionResponseResult.value = GuardianServiceResponseResult.success(it)
                    }
            }
            Log.v("Sections", "it took $elapsed ms")
        } catch (exception: Exception) {
            sectionResponseResult.value =
                GuardianServiceResponseResult.error(exception.toString(), null)

        } finally {
            dataLoading.set(false)
        }
    }

    private suspend fun processGettingArticles(
        sections: List<Section>,
        articleType: String
    ): List<Section> = withContext(Dispatchers.Default) {
        sections.map { async { getArticlesWith(it, articleType) } }.awaitAll()
        sections.filter { !it.articles.isNullOrEmpty() }
    }

    private suspend fun getArticlesWith(section: Section, articleType: String = ""): Section {
        try {
            section.articles =
                section.sectionName?.let { it -> repository.getArticles(it, articleType).data }
        } catch (exception: Exception) {
            //do nothing...
        }
        return section
    }

    fun getSectionResponseResult(): LiveData<GuardianServiceResponseResult<List<Section>>> =
        sectionResponseResult
}



