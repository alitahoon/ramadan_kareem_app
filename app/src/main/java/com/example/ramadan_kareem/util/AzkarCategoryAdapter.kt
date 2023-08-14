package com.example.ramadan_kareem.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.azkar.AzkarRespons
import com.example.ramadan_kareem.R

class AzkarCategoryAdapter(val context: Context, val listAzkarCategory: List<AzkarRespons>,val myListener:AzkarItemListener): RecyclerView.Adapter<AzkarCategoryAdapter.AzkarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarViewHolder {
        return AzkarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_azkar_category, parent, false), myListener)
    }

    override fun getItemCount(): Int {
        return listAzkarCategory.size
    }

    override fun onBindViewHolder(holder: AzkarViewHolder, position: Int) {
        val azkarCategory = listAzkarCategory[position]
        holder.bind(azkarCategory.category)
    }

    class AzkarViewHolder(itemView: View,myListener:AzkarItemListener) : RecyclerView.ViewHolder(itemView) {
        val nameCategory = itemView.findViewById<TextView>(R.id.azkar_name_category)

        init {
            itemView.setOnClickListener {
                myListener.onAzkarClicked(adapterPosition)
            }
        }

        fun bind(name:String){
            nameCategory.text = name
        }
    }

}