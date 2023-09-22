package com.example.ramadan_kareem.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.quran.Ayah
import com.example.domain.entity.quran.Surah
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.AyaRcvLayoutBinding
import com.example.ramadan_kareem.databinding.QuranSurahRcvItemLayoutBinding

class AyaCustomAdapter(val context: Context, val surah: Surah, val ayahItemListener: AyahItemListener) :
    RecyclerView.Adapter<AyaCustomAdapter.AyaViewHolder>() {
    private var binding:AyaRcvLayoutBinding?=null

    class AyaViewHolder(val binding: AyaRcvLayoutBinding, val listener:AyahItemListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ayah: Ayah){

            binding.ayah=ayah
            binding.listener=listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyaViewHolder {
       binding= AyaRcvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return AyaViewHolder(binding!!,ayahItemListener)
    }


    override fun getItemCount(): Int = surah.ayahs.size

    override fun onBindViewHolder(holder: AyaViewHolder, position: Int) {
        val item=surah.ayahs.get(position)
        holder.bind(item!!)
        holder.binding.ayaRcvLayoutLottiSave.setOnClickListener {
            holder.binding.ayaRcvLayoutLottiSave.playAnimation()
        }
        holder.binding.ayaRcvLayoutImgPlay.setOnClickListener {
            if (holder.binding.ayaRcvLayoutImgPlay.drawable==holder.binding.ayaRcvLayoutImgPlay.context.getDrawable(R.drawable.baseline_play_circle_filled_24)){
                holder.binding.ayaRcvLayoutImgPlay.setImageDrawable(holder.binding.ayaRcvLayoutImgPlay.context.getDrawable(R.drawable.baseline_stop_circle_24))
            }else{
                holder.binding.ayaRcvLayoutImgPlay.setImageDrawable(holder.binding.ayaRcvLayoutImgPlay.context.getDrawable(R.drawable.baseline_play_circle_filled_24))
            }
        }
    }
}