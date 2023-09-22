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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlQuranBinding.inflate(inflater,container,false)
            .apply {
               this.viewmodel=alQuranViewModel
            }


        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alQuranViewModel.getQuran()
        setObservers()

    }

    private fun setObservers() {
        lifecycleScope.launch (Dispatchers.IO) {
            alQuranViewModel.quran.collect{
                when(it){
                    is Resource.Success->{

                        Log.i(TAG,"${it.data}")
                        lifecycleScope.launch(Dispatchers.Main){
                            binding!!.alQuranRcvQuran.adapter=QuranCustomAdapter(requireContext(),it.data.data.surahs,this@AlQuran)
                            binding!!.alQuranProhressbar.visibility=View.INVISIBLE
                            binding!!.alQuranRcvQuran.visibility=View.VISIBLE
                        }

                    }
                    is Resource.Failure->{
                        Log.e(TAG,"${it.error}")
                        binding!!.alQuranRcvQuran.visibility=View.VISIBLE
                        binding!!.alQuranProhressbar.visibility=View.INVISIBLE
                    }
                    is Resource.Loading->{
                        Log.i(TAG,"getting qran from api")
                        binding!!.alQuranProhressbar.visibility=View.VISIBLE
                        binding!!.alQuranRcvQuran.visibility=View.INVISIBLE
                    }

                    else -> {}
                }
            }
        }
    }

    companion object {

    }

    override fun onSurahClicked(surah: Surah) {
        var dialog = Surah_ayas_viewer(surah)
        var childFragmentManager = getChildFragmentManager()
        dialog.show(childFragmentManager, "SurahViewer")
    }
}