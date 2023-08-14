package com.example.ramadan_kareem.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.azkar.AzkarRespons
import com.example.ramadan_kareem.databinding.FragmentAzkarBinding
import com.example.ramadan_kareem.util.AzkarCategoryAdapter
import com.example.ramadan_kareem.util.AzkarItemListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class Azkar : Fragment() {

    private var _binding: FragmentAzkarBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private lateinit var data: List<AzkarRespons>
    val azkarViewModel:AzkarViewModel by viewModels()
    private val TAG="AlAzkar"

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
        _binding = FragmentAzkarBinding.inflate(inflater, container, false)

        azkarViewModel.getAzkar()
        binding.rvAzkarCategory.layoutManager = LinearLayoutManager(context)
        setObservers()

        binding.goBackFromAzkar.setOnClickListener {
            val action = AzkarDirections.actionAzkarToHome()
            mNavController.navigate(action)
        }

        return binding.root
    }

    private fun setObservers() {
        lifecycleScope.launch (Dispatchers.IO) {
            azkarViewModel.azkar.collect{
                when(it){
                    is Resource.Loading->{
                        binding.azkarCategoryProgressBar.isVisible = true
                        Log.i(TAG,"getting azkar from json")
                    }
                    is Resource.Success->{
                        data = it.data.distinctBy { it.category }
                        withContext(Dispatchers.Main){
                            val adapter = AzkarCategoryAdapter(requireContext(),data, object :AzkarItemListener{
                                override fun onAzkarClicked(position: Int) {
                                    val action = AzkarDirections.actionAzkarToDisplayAzkar(data[position].category,it.data.toTypedArray())
                                    mNavController.navigate(action)
                                }

                            })
                            binding.rvAzkarCategory.adapter = adapter
                            binding.rvAzkarCategory.setHasFixedSize(true)
                        }
                        binding.azkarCategoryProgressBar.isVisible = false
                    }
                    is Resource.Failure->{
                        binding.azkarCategoryProgressBar.isVisible = false
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