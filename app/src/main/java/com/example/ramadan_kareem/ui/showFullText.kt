package com.example.ramadan_kareem.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentShowFullTextBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

class showFullText (private val ayaContent:String): DialogFragment() {
    private  var binding:FragmentShowFullTextBinding?=null
    private val TAG="showFullText"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"aya text ${ayaContent}")
        binding= FragmentShowFullTextBinding.inflate(inflater,container,false)
            .apply {
                this.ayaText=ayaContent
            }
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding!!.root
    }

    companion object {

    }
}