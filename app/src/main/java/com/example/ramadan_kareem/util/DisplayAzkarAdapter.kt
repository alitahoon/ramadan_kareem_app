package com.example.ramadan_kareem.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.azkar.AzkarRespons
import com.example.ramadan_kareem.R

class DisplayAzkarAdapter(val context: Context, val listAzkar: List<AzkarRespons>): RecyclerView.Adapter<DisplayAzkarAdapter.AzkarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarViewHolder {
        return AzkarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_display_azkar, parent, false))
    }

    override fun getItemCount(): Int {
        return listAzkar.size
    }

    override fun onBindViewHolder(holder: AzkarViewHolder, position: Int) {
        val azkar = listAzkar[position]
        holder.bind(azkar)
    }

    class AzkarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val zker = itemView.findViewById<TextView>(R.id.tv_zekr)
        val description = itemView.findViewById<TextView>(R.id.tv_zekr_description)
        val count = itemView.findViewById<TextView>(R.id.tv_count)

        fun bind(azkar:AzkarRespons){
            zker.text = azkar.zekr
            if (azkar.count != ""){
                count.text = " (${azkar.count}) "
            }
            description.text = azkar.description
        }
    }

}