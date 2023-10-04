package com.example.ramadan_kareem.ui

import Resource
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.GetCurrantLocationJustOnce
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.usecase.GetAzanFromRemote
import com.example.domain.usecase.GetUserCurrantLocation
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
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
    private val getAzanFromRemote: GetAzanFromRemote,
    private val context: Context
) : ViewModel() {

    private val TAG = "HomeViewModel"
    private val _userLocation = MutableStateFlow<LatLng>(LatLng(0.0, 0.0))
    val userLocation: StateFlow<LatLng> = _userLocation

    private val _azanResponse= MutableLiveData<AzanResponse>(null)
    val azanResponse:LiveData<AzanResponse> = _azanResponse


    fun getAzan() {
        viewModelScope.launch(Dispatchers.Main) {
            getUserCurrantLocation {
                when (it) {
                    is Resource.Failure -> {
                        Log.e(TAG, "${it.error}")
                    }

                    is Resource.Success -> {
                        //get Currant year
                        Log.d(TAG, "${it.data}")
                        val calendar = Calendar.getInstance()
                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH)
                        viewModelScope.launch(Dispatchers.Main) {
                            getAzanFromRemote(
                                month,
                                year,
                                LatLng(it.data.latitude, it.data.longitude)
                            ) {
                                when (it) {
                                    is Resource.Success -> {
                                        Log.d(TAG, "${it.data}")
                                        //store data in sharedPreferences
                                        _azanResponse.value=it.data!!
                                        saveAzandata(it.data)
                                    }

                                    is Resource.Failure -> {
                                        Log.e(TAG, "${it.error}")
                                    }

                                    is Resource.Loading -> {}
                                    else -> {

                                    }
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {}
                    else -> {}
                }
            }
        }
    }
    fun saveAzandata(azanResponse:AzanResponse){
        val AZAN_PREF_KEY = "azanResponse"
        val gson = Gson()
        val sharedPreferences = context.getSharedPreferences("azanResponsePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val jsonString = gson.toJson(azanResponse)
        editor.putString(AZAN_PREF_KEY, jsonString)
        editor.apply()
    }

}


