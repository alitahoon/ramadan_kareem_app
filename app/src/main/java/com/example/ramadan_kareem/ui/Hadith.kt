package com.example.ramadan_kareem.ui

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.Resource
import com.example.domain.entity.hadith.Data
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentHadithBinding
import com.example.ramadan_kareem.util.QuranCustomAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class Hadith : Fragment() {

    private var _binding: FragmentHadithBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private lateinit var data: ArrayList<Data>
    private val TAG="AlHadith"
    private val hadithViewModel:HadithViewModel by viewModels()

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

        hadithViewModel.getHadith()

        binding.rvHadith.layoutManager = LinearLayoutManager(context)

        setObservers()


        return binding.root
    }

    private fun setObservers() {
        lifecycleScope.launch (Dispatchers.IO) {
            hadithViewModel.hadith.collect{
                when(it){
                    is Resource.Loading->{
                        binding.hadithProgressBar.isVisible = true
                        Log.i(TAG,"getting hadith from api")
                    }
                    is Resource.Success->{
                        data = it.data.hadiths.data
                        withContext(Dispatchers.Main){
                            val adapter = HadithAdapter(requireContext(),data)
                            binding.rvHadith.adapter = adapter
                            binding.rvHadith.setHasFixedSize(true)
                        }
                        binding.hadithProgressBar.isVisible = false
                    }
                    is Resource.Failure->{
                        binding.hadithProgressBar.isVisible = false
                        Log.e(TAG,"${it.error}")
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}