package com.example.ramadan_kareem.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.hadith.Data
import com.example.ramadan_kareem.R

class HadithAdapter(val context:Context,val listHadith: ArrayList<Data>): RecyclerView.Adapter<HadithAdapter.HadithViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HadithViewHolder {
        return HadithViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_hadith, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listHadith.size
    }

    override fun onBindViewHolder(holder: HadithViewHolder, position: Int) {
        val hadith = listHadith[position]
        holder.bind(hadith.hadithArabic)
    }

    class HadithViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHadith = itemView.findViewById<TextView>(R.id.tv_hadith)

        fun bind(hadith:String){
            tvHadith.text = hadith
        }
    }

}