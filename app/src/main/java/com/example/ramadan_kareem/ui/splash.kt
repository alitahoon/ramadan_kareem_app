package com.example.ramadan_kareem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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