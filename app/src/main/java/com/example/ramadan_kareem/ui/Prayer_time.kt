package com.example.ramadan_kareem.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentPrayerTimeBinding
import com.example.ramadan_kareem.util.getAzanData

class Prayer_time : Fragment() {
    private val TAG="Prayer_time"
    private val prayerTimeviewmodel:Prayer_timeViewmodel by activityViewModels()
    private var binding:FragmentPrayerTimeBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"${getAzanData(requireContext())}")
        binding= FragmentPrayerTimeBinding.inflate(inflater,container,false)
            .apply{
                this.azan=getAzanData(requireContext())
            }

       return binding!!.root
    }

    companion object {

    }
}