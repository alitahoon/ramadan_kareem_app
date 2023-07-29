package com.example.ramadan_kareem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.hadith.Data
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentHadithBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Hadith : Fragment() {

    private var _binding: FragmentHadithBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private lateinit var data: ArrayList<Data>
    private val viewModel:HadithViewModel by viewModels()

    companion object {

    }

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
        _binding = FragmentHadithBinding.inflate(inflater, container, false)

//        data.add("فَيَفْصِمُ عَنْهُ وَإِنَّ جَبِينَهُ لَيَتَفَصَّدُ عَرَقًا")

        viewModel.getHadith()

        binding.rvHadith.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.hadith.collect{
                data = it?.hadiths?.data!!
                Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show()
                val adapter = HadithAdapter(requireContext(),data)
                binding.rvHadith.adapter = adapter
                binding.rvHadith.setHasFixedSize(true)
            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}