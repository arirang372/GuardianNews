package com.sung.guardiannews.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.sung.guardiannews.data.GuardianNewsRepository
import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class GuardianDashboardViewModel @Inject constructor(
    private val repository: GuardianNewsRepository
) : ViewModel() {
    val dataLoading = ObservableBoolean(false)

    private val sectionResponseResult =
        MutableLiveData<GuardianServiceResponseResult<List<Section>>>()

    val selectedTab = MutableLiveData<String>()

    val articleResponseResult: LiveData<GuardianServiceResponseResult<List<Article>>> =
        selectedTab.switchMap { tab ->
            dataLoading.set(true)
            liveData(Dispatchers.IO + exceptionHandler) {
                emit(repository.getArticles(tab))
                dataLoading.set(false)
            }
        }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Exception", "Exception! - ${throwable.message}")
        dataLoading.set(false)
    }

    init {
        getSections()
    }

    private fun getSections() = viewModelScope.launch(Dispatchers.IO) {
        dataLoading.set(true)
        try {
//            for(i in 1 .. 10){
//
//            }
//            repository.getSections().response.results.forEach {
//                CoroutineScope(Dispatchers.IO).launch {
//                    it.articles =
//                        withContext(Dispatchers.Default){
//                            repository.getArticles(it.sectionName).data?.let{it.take(10)}
//                        }!!
//                }
//            }.also {
//                GuardianServiceResponseResult.success(it)
//            }
            val response = repository.getSections().response
            for(i in 0 .. 4){
                CoroutineScope(Dispatchers.IO).launch {
                    response.results[i].articles =
                        withContext(Dispatchers.Default){
                            repository.getArticles(response.results[i].sectionName).data
                        }!!
                }
            }
            sectionResponseResult.postValue(GuardianServiceResponseResult.success(response.results))
        } catch (exception: Exception) {
            sectionResponseResult.postValue(
                GuardianServiceResponseResult.error(exception.toString(), null)
            )
        } finally {
            dataLoading.set(false)
        }
    }

    fun getSectionResponseResult(): LiveData<GuardianServiceResponseResult<List<Section>>> =
        sectionResponseResult
}



