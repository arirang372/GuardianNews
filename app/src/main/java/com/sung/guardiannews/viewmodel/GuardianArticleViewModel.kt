package com.sung.guardiannews.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.sung.guardiannews.data.GuardianNewsRepository
import com.sung.guardiannews.model.Field
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GuardianArticleViewModel @Inject constructor(
    private val repository: GuardianNewsRepository
) : ViewModel(){

    val field = ObservableField<Field>()

}