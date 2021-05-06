//package com.sung.guardiannews
//
//import android.app.Application
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.sung.guardiannews.data.GuardianNewsRepository
//import com.sung.guardiannews.data.GuardianNewsService
////import com.sung.guardiannews.data.local.GuardianNewsDatabase
//import com.sung.guardiannews.viewmodel.GuardianDashboardViewModel
//
//class ViewModelFactory private constructor(
//    private val application: Application,
//    private val mRepository: GuardianNewsRepository
//) :
//    ViewModelProvider.NewInstanceFactory() {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(GuardianDashboardViewModel::class.java)) {
//            return GuardianDashboardViewModel(mRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class : " + modelClass.name)
//    }
//
//    companion object {
//        private var instance: ViewModelFactory? = null
//        fun getInstance(application: Application): ViewModelFactory {
//            if (instance == null)
//                instance = ViewModelFactory(
//                    application,
//                    GuardianNewsRepository(
//                        GuardianNewsService.create()
//                    )
//                )
//            return instance as ViewModelFactory
//        }
//    }
//}