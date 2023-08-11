package com.example.ramadan_kareem.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class splash : AppCompatActivity() {

    private val splashViewModel:SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
            .apply {
                this.viewmodel=splashViewModel
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(binding.root)
        setObservers()
    }

    private fun setObservers() {
        splashViewModel.cointinueBtnClicked.observe(this, Observer {
                if (it==true){
                }
        })
    }
}