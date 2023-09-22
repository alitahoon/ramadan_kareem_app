package com.example.ramadan_kareem.ui

import Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.GetCurrantLocationJustOnce
import com.example.domain.usecase.GetAzanFromRemote
import com.example.domain.usecase.GetUserCurrantLocation
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserCurrantLocation: GetUserCurrantLocation,
    private val getAzanFromRemote: GetAzanFromRemote
) :ViewModel(){
    private val TAG="HomeViewModel"
    private val _userLocation=MutableStateFlow<LatLng>(LatLng(0.0,0.0))
        val userLocation:StateFlow<LatLng> = _userLocation




    fun getAzan(){
        viewModelScope.launch (Dispatchers.Main){
           getUserCurrantLocation{
               when(it){
                   is Resource.Failure->{
                       Log.e(TAG,"${it.error}")
                   }
                   is Resource.Success->{
                       //get Currant year
                       Log.d(TAG,"${it.data}")
                       val calendar = Calendar.getInstance()
                       val year = calendar.get(Calendar.YEAR)
                       val month = calendar.get(Calendar.MONTH)
                       viewModelScope.launch (Dispatchers.Main){
                           getAzanFromRemote(month,year,LatLng(it.data.latitude,it.data.longitude)){
                              when(it){
                                  is Resource.Success->{
                                      Log.d(TAG,"${it.data}")
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
                   is Resource.Loading->{}
                   else -> {}
               }
           }
        }
    }
}