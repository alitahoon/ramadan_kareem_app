package com.example.ramadan_kareem.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.entity.quran.Surah
import com.example.ramadan_kareem.databinding.FragmentAlQuranBinding
import com.example.ramadan_kareem.util.QuranCustomAdapter
import com.example.ramadan_kareem.util.SurahItemListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class AlQuran : Fragment() ,SurahItemListener{
    private var binding:FragmentAlQuranBinding?=null
    private val alQuranViewModel:AlQuranViewModel by activityViewModels()
    private val TAG="AlQuran"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alQuranViewModel.getQuran()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlQuranBinding.inflate(inflater,container,false)
            .apply {
               this.viewmodel=alQuranViewModel
            }

        setObservers()

        return binding!!.root
    }

    private fun setObservers() {
        lifecycleScope.launch (Dispatchers.Main) {
            alQuranViewModel.quran.collect{
                when(it){
                    is Resource.Success->{
                        Log.i(TAG,"${it.data}")
                        binding!!.alQuranRcvQuran.adapter=QuranCustomAdapter(requireContext(),it.data.data.surahs,this@AlQuran)

                    }
                    is Resource.Failure->{
                        Log.e(TAG,"${it.error}")
                    }
                    is Resource.Loading->{
                        Log.i(TAG,"getting qran from api")
                    }

                    else -> {}
                }
            }
        }
    }

    companion object {

    }

    override fun onSurahClicked(surah: Surah) {

    }
}