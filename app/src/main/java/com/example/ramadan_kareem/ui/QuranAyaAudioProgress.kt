package com.example.ramadan_kareem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentQuranAyaAudioProgressBinding


class QuranAyaAudioProgress : Fragment() {
    private var binding:FragmentQuranAyaAudioProgressBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentQuranAyaAudioProgressBinding.inflate(inflater,container,false)
            .apply {

            }

        return binding!!.root
    }

    companion object {

    }
}