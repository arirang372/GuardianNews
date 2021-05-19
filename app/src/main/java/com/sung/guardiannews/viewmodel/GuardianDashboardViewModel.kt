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
                                repository.getArticles(
                                    it.sectionName
                                ).data
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
    fun getSectionResponseResult(): LiveData<GuardianServiceResponseResult<List<Section>>> =
        sectionResponseResult

}



