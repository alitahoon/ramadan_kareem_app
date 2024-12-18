package com.example.ramadan_kareem.ui

import Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.quran.QuranResponse
import com.example.domain.entity.quran_audio.QuranAudioResponse
import com.example.domain.usecase.GetQuranFromRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlQuranViewModel @Inject constructor(
    private val getQuranFromRemote: GetQuranFromRemote
) :ViewModel(){
    private val TAG = "AlQuranViewModel"

    private val _quran: MutableStateFlow<Resource<QuranAudioResponse>?> = MutableStateFlow(null)
    val quran: StateFlow<Resource<QuranAudioResponse>?> = _quran

    fun getQuran(){
        _quran.value=Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getQuranFromRemote{
                _quran.value=it
            }
            Log.d(TAG,"getQuran")
        }
    }

}