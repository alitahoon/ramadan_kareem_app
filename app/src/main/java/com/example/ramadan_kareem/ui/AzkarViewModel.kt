package com.example.ramadan_kareem.ui

import Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.azkar.AzkarRespons
import com.example.domain.usecase.GetAzkar
import com.example.domain.usecase.GetAzkarCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarViewModel @Inject constructor(
    private val getAzkarCategory: GetAzkarCategory,
    private val getAzkar: GetAzkar
):ViewModel() {

    private val TAG = "AzkarViewModel"

    private val errorHandler = CoroutineExceptionHandler{ _,throwable ->
        throwable.printStackTrace()
    }

    private val _azkarCategory: MutableStateFlow<Resource<List<AzkarRespons>>?> = MutableStateFlow(null)
    val azkarCategory: StateFlow<Resource<List<AzkarRespons>>?> = _azkarCategory

    private val _azkar: MutableStateFlow<Resource<List<AzkarRespons>>?> = MutableStateFlow(null)
    val azkar: StateFlow<Resource<List<AzkarRespons>>?> = _azkar

    fun getAzkarCategory() = viewModelScope.launch(errorHandler) {
        _azkarCategory.value = Resource.Loading
        getAzkarCategory{
            _azkarCategory.value = it
        }
        Log.d(TAG,"getAzkarCategory")
    }

//    fun getAzkar() = viewModelScope.launch(errorHandler) {
//        _azkar.value = Resource.Loading
//        getAzkar{
//            _azkar.value = it
//        }
//        Log.d(TAG,"getAzkar")
//    }

}