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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
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

//    val articleResponseResult: LiveData<GuardianServiceResponseResult<List<Article>>> =
//        sectionName.switchMap { name ->
//            dataLoading.set(true)
//            liveData(Dispatchers.IO + exceptionHandler) {
//                emit(repository.getArticles(name))
//                dataLoading.set(false)
//            }
//        }
//
//    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        Log.e("Exception", "Exception! - ${throwable.message}")
//        dataLoading.set(false)
//    }

    init {
        getSections()
    }

    private fun getSections() = viewModelScope.launch {
        dataLoading.set(true)
        try {
            val elapsed = measureTimeMillis {
                val response = repository.getSections().response
                response.results.map { async { processResult(it) } }.awaitAll()
                sectionResponseResult.postValue(GuardianServiceResponseResult.success(response.results))
            }
            Log.v("Sections", "it took $elapsed ms")
        } catch (exception: Exception) {
            sectionResponseResult.postValue(
                GuardianServiceResponseResult.error(exception.toString(), null)
            )
        } finally {
            dataLoading.set(false)
        }
    }

    private suspend fun processResult(section: Section): Section {
        try {
            section.articles = section.sectionName?.let { it -> repository.getArticles(it).data }
        } catch (exception: Exception) {
            //do nothing...
        }
        return section
    }

    fun getSectionResponseResult(): LiveData<GuardianServiceResponseResult<List<Section>>> = sectionResponseResult
}



