package com.example.ramadan_kareem.ui

import Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.azkar.AzkarRespons
import com.example.domain.usecase.GetAzkarCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarViewModel @Inject constructor(
    private val getAzkarCategory: GetAzkarCategory
):ViewModel() {

    private val TAG = "AzkarViewModel"

    private val errorHandler = CoroutineExceptionHandler{ _,throwable ->
        throwable.printStackTrace()
    }

    private val _azkar: MutableStateFlow<Resource<List<AzkarRespons>>?> = MutableStateFlow(null)
    val azkar: StateFlow<Resource<List<AzkarRespons>>?> = _azkar

    fun getAzkar() = viewModelScope.launch(errorHandler) {
        _azkar.value = Resource.Loading
        getAzkarCategory{
            _azkar.value = it
        }
        Log.d(TAG,"getAzkarCategory")
    }

}