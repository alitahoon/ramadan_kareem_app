package com.example.ramadan_kareem.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.quran_audio.Surah
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.QuranSurahRcvItemLayoutBinding

class QuranCustomAdapter(val context: Context, val quransurahList:List<Surah>, val surahItemListener: SurahItemListener) :
    RecyclerView.Adapter<QuranCustomAdapter.QuranviewHolder>() {
    private var binding:QuranSurahRcvItemLayoutBinding?=null

    class QuranviewHolder(val binding: QuranSurahRcvItemLayoutBinding, val listener:SurahItemListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(surah: Surah){
            binding.surah=surah
            binding.listener=listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranviewHolder {
       binding= QuranSurahRcvItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return QuranviewHolder(binding!!,surahItemListener)
    }


    override fun getItemCount(): Int = quransurahList.size

    override fun onBindViewHolder(holder: QuranviewHolder, position: Int) {
        val item=quransurahList.get(position)
        holder.bind(item!!)
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rcv_quran_items_animation)
    }
}