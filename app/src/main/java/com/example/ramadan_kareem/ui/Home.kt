package com.example.ramadan_kareem.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel:HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCurrantTime() {
        val currentTime = LocalTime.now()
        val hours = currentTime.hour
        val minutes = currentTime.minute
        Toast.makeText(requireContext(),("Current time: $hours:$minutes"), Toast.LENGTH_SHORT).show()

        _binding!!.homeCurrantTimeTxt.setText("${hours}:${minutes}")

    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        binding.qiblaCard.setOnClickListener {
            val action = HomeDirections.actionHomeToQibla()
            mNavController.navigate(action)
        }
        binding.prayer.setOnClickListener {
            val action = HomeDirections.actionHomeToPrayerTime()
            mNavController.navigate(action)
        }
        setCurrantTime()
        homeViewModel.getAzan()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}