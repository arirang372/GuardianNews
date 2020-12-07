package com.sung.guardiannews.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.sung.guardiannews.data.GuardianNewsRepository
import com.sung.guardiannews.data.remote.GuardianServiceResponseResult
import com.sung.guardiannews.model.Content
import com.sung.guardiannews.model.Section
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuardianDashboardViewModel(
    private val repository: GuardianNewsRepository
) : ViewModel() {
    val dataLoading = ObservableBoolean(false)

    private val sectionResponseResult =
        MutableLiveData<GuardianServiceResponseResult<List<Section>>>()

    val selectedTab = MutableLiveData<String>()

    val contentResponseResult: LiveData<GuardianServiceResponseResult<List<Content>>> =
        selectedTab.switchMap { tab ->
            dataLoading.set(true)
            liveData(Dispatchers.IO + exceptionHandler) {
                emit(repository.getNewsContent(tab))
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
            sectionResponseResult.postValue(
                GuardianServiceResponseResult.success(repository.getSections().response.results)
            )
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



