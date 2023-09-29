package com.example.ramadan_kareem.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.quran_audio.Ayah
import com.example.domain.entity.quran_audio.Surah
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.AyaRcvLayoutBinding
import com.ramotion.foldingcell.FoldingCell
import java.util.Collections


class AyaCustomAdapter(val context: Context, val surah: Surah, val ayahItemListener: AyahItemListener) :
    RecyclerView.Adapter<AyaCustomAdapter.AyaViewHolder>() {
    private var binding:AyaRcvLayoutBinding?=null
    private var selectedItemPosition = RecyclerView.NO_POSITION // Initialize with an invalid position
    private var lastClickedPosition: Int = RecyclerView.NO_POSITION

    var selectedItemPos = -1
    var lastItemSelectedPos = -1


    fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Update the dataset to reflect the item move
        // This method will be called as the item is being dragged
        Collections.swap(surah.ayahs, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

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
        holder.bind(item)
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rcv_items_animation)
        holder.binding.ayaRcvItemBtnMore.setOnClickListener {
            holder.binding!!.ayaRcvItemFoldingLayout.toggle(false)
        }
//        holder.binding.ayaRcvLayoutImgPlay.setOnClickListener {
//            if (holder.binding.ayaRcvLayoutImgPlay.drawable==holder.binding.ayaRcvLayoutImgPlay.context.getDrawable(R.drawable.baseline_play_circle_filled_24)){
//                holder.binding.ayaRcvLayoutImgPlay.setImageDrawable(holder.binding.ayaRcvLayoutImgPlay.context.getDrawable(R.drawable.baseline_stop_circle_24))
//            }else{
//                holder.binding.ayaRcvLayoutImgPlay.setImageDrawable(holder.binding.ayaRcvLayoutImgPlay.context.getDrawable(R.drawable.baseline_play_circle_filled_24))
//            }
//        }
    }
}