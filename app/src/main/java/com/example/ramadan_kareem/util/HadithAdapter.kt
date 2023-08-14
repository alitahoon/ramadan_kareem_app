package com.example.ramadan_kareem.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.hadith.Data
import com.example.ramadan_kareem.R
import kr.co.prnd.readmore.ReadMoreTextView

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
        val bookName = hadith.chapter.chapterArabic
        holder.bind(hadith.hadithArabic,bookName)
    }

    class HadithViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHadith = itemView.findViewById<ReadMoreTextView>(R.id.tv_hadith)
        val bookName = itemView.findViewById<TextView>(R.id.hadith_book_name)

        fun bind(hadith:String,name:String){
            tvHadith.text = hadith
            bookName.text = name
        }
    }

}