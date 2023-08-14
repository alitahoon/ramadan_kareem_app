package com.example.ramadan_kareem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.azkar.AzkarRespons
import com.example.ramadan_kareem.databinding.FragmentDisplayAzkarBinding
import com.example.ramadan_kareem.util.DisplayAzkarAdapter

class DisplayAzkar : Fragment() {

    private var _binding: FragmentDisplayAzkarBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private lateinit var data: Array<AzkarRespons>
    private val TAG="DisplayAzkar"
    private val args by navArgs<DisplayAzkarArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
        data = args.azkar.filter { it.category == args.category }.toTypedArray()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDisplayAzkarBinding.inflate(inflater, container, false)

        binding.tvNameCategory.text = args.category

        binding.rvDisplayAzkar.layoutManager = LinearLayoutManager(context)
        val adapter = DisplayAzkarAdapter(requireContext(),data.toList())
        binding.rvDisplayAzkar.adapter = adapter
        binding.rvDisplayAzkar.setHasFixedSize(true)

        binding.goBackFromDisplayAzkar.setOnClickListener {
            val action = DisplayAzkarDirections.actionDisplayAzkarToAzkar()
            mNavController.navigate(action)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}