package com.example.ramadan_kareem

import android.app.Application
import androidx.databinding.BindingAdapter
import com.example.ramadan_kareem.util.BindingMethods
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ramadan_kareem: Application() {
    @Inject
    lateinit var bindingMethods:BindingMethods
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}