package com.example.ramadan_kareem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.azkar.AzkarRespons
import com.example.ramadan_kareem.databinding.FragmentDisplayAzkarBinding

class DisplayAzkar : Fragment() {

    private var _binding: FragmentDisplayAzkarBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private lateinit var data: List<AzkarRespons>
    private val displayAzkarViewModel:AzkarViewModel by viewModels()
    private val TAG="DisplayAzkar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
        data = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDisplayAzkarBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}