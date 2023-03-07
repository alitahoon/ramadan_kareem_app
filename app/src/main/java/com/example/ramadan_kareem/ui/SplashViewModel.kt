package com.example.ramadan_kareem.ui

import SingleLiveEvent
import android.view.View
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(

) :ViewModel() {
    var cointinueBtnClicked= SingleLiveEvent<Boolean>()

    fun onCointinueBtnClicked(view: View){
        cointinueBtnClicked.postValue(true)
    }
}