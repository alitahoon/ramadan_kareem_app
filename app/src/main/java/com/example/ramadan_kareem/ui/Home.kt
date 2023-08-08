package com.example.ramadan_kareem.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.hadithCard.setOnClickListener {
            val action = HomeDirections.actionHomeToHadith()
            mNavController.navigate(action)
        }
        binding.quran.setOnClickListener{
            val action = HomeDirections.actionHomeToAlQuran()
            mNavController.navigate(action)
        }
        binding.azkarCard.setOnClickListener {
            val action = HomeDirections.actionHomeToAzkar()
            mNavController.navigate(action)
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}