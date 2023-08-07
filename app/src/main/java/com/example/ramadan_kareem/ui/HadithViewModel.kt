package com.example.ramadan_kareem.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Resource
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.usecase.GetHadith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HadithViewModel @Inject constructor(
    private val getHadithUseCase: GetHadith
):ViewModel() {

    private val TAG = "HadithViewModel"

    private val errorHandler = CoroutineExceptionHandler{ _,throwable ->
        throwable.printStackTrace()
    }

    private val _hadith:MutableStateFlow<Resource<HadithResponse>?> = MutableStateFlow(null)
    val hadith:StateFlow<Resource<HadithResponse>?> = _hadith

    fun getHadith() = viewModelScope.launch(errorHandler) {
        _hadith.value = Resource.Loading
        getHadithUseCase{
            _hadith.value=it
        }
        Log.d(TAG,"getHadith")
    }


}