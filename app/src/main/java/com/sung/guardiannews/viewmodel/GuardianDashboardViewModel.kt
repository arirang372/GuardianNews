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
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
    private val sectionResponseResult =
        MutableLiveData<GuardianServiceResponseResult<List<Section>>>()

    val sectionNames = MutableLiveData<List<String>>()
    val sectionName = MutableLiveData<String>()

    val articleResponseResult: LiveData<GuardianServiceResponseResult<List<Article>>> =
        sectionName.switchMap { name ->
            dataLoading.set(true)
            liveData(Dispatchers.IO + exceptionHandler) {
                emit(repository.getArticles(name))
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

    val visibleItemCount = MutableLiveData<Int>()

    private fun getSections() = viewModelScope.launch(Dispatchers.IO) {
        dataLoading.set(true)
        try {
            val response = repository.getSections().response
            coroutineScope {
                response.results.forEach {
                    try {
                        it.articles =
                            withContext(Dispatchers.Default) {
                                repository.getArticles(
                                    it.sectionName
                                ).data
                            }!!
                    }
                    catch(exception : Exception){
                        it.articles = mutableListOf()
                    }
                }
                sectionResponseResult.postValue(GuardianServiceResponseResult.success(response.results))
            }
//            for(i in 0 .. 4){
//                CoroutineScope(Dispatchers.IO).launch {
//                    response.results[i].articles =
//                        withContext(Dispatchers.Default){
//                            repository.getArticles(response.results[i].sectionName).data
//                        }!!
//                }
//            }
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

    fun setSectionName(sectionName : String){
        this.sectionName.postValue(sectionName)
    }

    fun listScrolled(visibleItemCount : Int, lastVisibleItemPosition : Int, totalItemCount : Int){
        if(visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount){
            val immutableQuery = sectionName.value
//            if (immutableQuery != null) {
//                viewModelScope.launch {
//                    repository.setSectionName(immutableQuery)
//                }
//            }
        }
    }
}



