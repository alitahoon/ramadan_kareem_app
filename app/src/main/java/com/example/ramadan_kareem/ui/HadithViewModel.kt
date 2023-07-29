package com.example.ramadan_kareem.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.usecase.GetHadith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HadithViewModel @Inject constructor(
    private val getHadithUseCase: GetHadith
):ViewModel() {

    private val TAG = "HadithViewModel"

    private val _hadith:MutableStateFlow<HadithResponse?> = MutableStateFlow(null)
    val hadith:StateFlow<HadithResponse?> = _hadith

    fun getHadith(){
        viewModelScope.launch {
            _hadith.value = getHadithUseCase()
            Log.d(TAG,"getHadith")
        }
    }

}