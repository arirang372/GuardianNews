package com.sung.guardiannews.viewmodel

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

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

    private fun getSections() = viewModelScope.launch(Dispatchers.IO) {
        dataLoading.set(true)
        try {
            val response = repository.getSections().response
            coroutineScope {
                response.results.forEach {
                    try {
                        it.articles =
                            withContext(Dispatchers.Default) {
                                it.sectionName?.let { sectionId ->
                                    repository.getArticles(
                                        sectionId
                                    ).data
                                }
                            }!!
                    } catch (exception: Exception) {
                        //do nothing...
                    }
                }
                sectionResponseResult.postValue(GuardianServiceResponseResult.success(response.results))
            }
        } catch (exception: Exception) {
            sectionResponseResult.postValue(
                GuardianServiceResponseResult.error(exception.toString(), null)
            )
        } finally {
            dataLoading.set(false)
        }
    }

    /**
     *  Trying to handle it through Flow
     *
     */
//    private fun getSections() = viewModelScope.launch(Dispatchers.IO) {
//        dataLoading.set(true)
//        try {
//            var response = repository.getSections().response
//            var sections = response.results
//            runBlocking {
//                val deferreds: List<Deferred<Unit>> = sections.map {
//                    async {
//                        delay(500)
//                        it.articles = it.sectionName?.let { it1 -> getSectionArticles(it1) }
//                    }
//                }
//                deferreds.awaitAll()
//                sectionResponseResult.postValue(GuardianServiceResponseResult.success(sections))
//            }
//        } catch (exception: Exception) {
//            sectionResponseResult.postValue(
//                GuardianServiceResponseResult.error(exception.toString(), null)
//            )
//        } finally {
//            dataLoading.set(false)
//        }
//    }

    private suspend fun getSectionArticles(sectionId: String) =
        repository.getListArticles(sectionId)


    fun getSectionResponseResult(): LiveData<GuardianServiceResponseResult<List<Section>>> =
        sectionResponseResult

}



