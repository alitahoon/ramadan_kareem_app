package com.example.ramadan_kareem.ui

import Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAyaAudioLinkFromRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Surah_ayas_viewer_Viewmodel @Inject constructor(
    private val getAyaAudioLinkFromRemote: GetAyaAudioLinkFromRemote
) :ViewModel() {
    private val TAG="Surah_ayas_viewer"
    private val _ayaAudioLink=MutableStateFlow<String>("")
        val ayaAudioLink:StateFlow<String> = _ayaAudioLink

    fun getAyaAudioLink(ayaNumber:Int){
        viewModelScope.launch (Dispatchers.IO){
            getAyaAudioLinkFromRemote(ayaNumber){
                when(it){
                    is Resource.Success->{
                        Log.d(TAG,"Aya Audio Link -> ${it.data}")
                        _ayaAudioLink.value=it.data
                    }
                    is Resource.Failure->{
                        Log.e(TAG,"${it.error}")
                    }
                    is Resource.Loading->{}
                    else -> {}
                }
            }
        }
    }
}