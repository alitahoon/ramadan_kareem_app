package com.example.ramadan_kareem.util

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.domain.entity.azan.AzanResponse
import com.google.gson.Gson


private const val AZAN_PREF_KEY = "azanResponse"
private val gson = Gson()

// Save an object to SharedPreferences
fun Fragment.saveAzanData(context: Context, azanResponse: AzanResponse?) {
    val sharedPreferences = context.getSharedPreferences("azanResponsePrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val jsonString = gson.toJson(azanResponse)
    editor.putString(AZAN_PREF_KEY, jsonString)
    editor.apply()
}

// Save an object to SharedPreferences
fun Context.saveAzanData(azanResponse: AzanResponse?) {
    val sharedPreferences = getSharedPreferences("azanResponsePrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val jsonString = gson.toJson(azanResponse)
    editor.putString(AZAN_PREF_KEY, jsonString)
    editor.apply()
}

// Retrieve the object from SharedPreferences
fun Context.getAzanData(): AzanResponse? {
    val sharedPreferences = getSharedPreferences("azanResponsePrefs", Context.MODE_PRIVATE)
    val jsonString = sharedPreferences.getString(AZAN_PREF_KEY, null)
    return if (jsonString != null) {
        gson.fromJson(jsonString, AzanResponse::class.java)
    } else null
}

fun Fragment.getAzanData(context: Context): AzanResponse? {
    val sharedPreferences = context.getSharedPreferences("azanResponsePrefs", Context.MODE_PRIVATE)
    val jsonString = sharedPreferences.getString(AZAN_PREF_KEY, null)
    return if (jsonString != null) {
        gson.fromJson(jsonString, AzanResponse::class.java)
    } else null
}