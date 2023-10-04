package com.example.ramadan_kareem.util

import Resource
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.quran_audio.Ayah
import com.example.domain.entity.quran_audio.Surah
import com.example.domain.usecase.GetTafsirForAya
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.AyaRcvLayoutBinding
import com.example.ramadan_kareem.ui.showFullText
import com.ramotion.foldingcell.FoldingCell
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject
import javax.inject.Singleton


class AyaCustomAdapter(val getTafsirForAya: GetTafsirForAya,val context: Context, val surah: Surah, val ayahItemListener: AyahItemListener,val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<AyaCustomAdapter.AyaViewHolder>() {
    private var binding:AyaRcvLayoutBinding?=null
    private var TAG="AyaCustomAdapter"
    val coroutineScope: CoroutineScope = MainScope()



    class AyaViewHolder(val binding: AyaRcvLayoutBinding, val listener:AyahItemListener,val surah_id: Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ayah: Ayah){
            binding.ayah=ayah
            binding.suraId=surah_id
            binding.listener=listener
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyaViewHolder {
       binding= AyaRcvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return AyaViewHolder(binding!!,ayahItemListener,surah.number)
    }


    override fun getItemCount(): Int = surah.ayahs.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: AyaViewHolder, position: Int) {
        val item=surah.ayahs.get(position)
        holder.bind(item)
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rcv_items_animation)
        holder.binding.ayaRcvItemBtnMore.setOnClickListener {
            holder.binding!!.ayaRcvItemFoldingLayout.toggle(false)
        }

        holder.binding.ayaRcvLayoutTxtAyaArabic.setOnClickListener{
            if (item.text.length > 250){
                Log.d(TAG,"${item.text}")
                var dialog = showFullText(item.text)
                dialog.show(fragmentManager, "ShowFullText")
            }
        }
        holder.binding.ayaRcvLayoutTxttafsirContent.setOnClickListener {
            if(holder.binding.ayaRcvLayoutTxttafsirContent.lineCount>5){
                var dialog = showFullText(holder.binding.ayaRcvLayoutTxttafsirContent.text.toString())
                dialog.show(fragmentManager, "ShowFullText")
            }
        }
        holder.binding.ayaRcvLayoutTxtEnglish.setOnClickListener {
            if(holder.binding.ayaRcvLayoutTxttafsirContent.lineCount>5){
                var dialog = showFullText(holder.binding.ayaRcvLayoutTxttafsirContent.text.toString())
                dialog.show(fragmentManager, "ShowFullText")
            }
        }

        holder.binding.ayaRcvItemBtnLess.setOnClickListener {
            holder.binding.ayaRcvItemFoldingLayout.toggle(false)
        }

        holder.binding.ayaItemTxtAutoComplite.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            //get tafsir from remote
            Log.d(TAG,"item position ----> $position")
            coroutineScope.launch (Dispatchers.IO){
                getTafsirForAya(position+1,surah.number,item.numberInSurah){
                    when (it){
                        is Resource.Success->{
                            coroutineScope.launch (Dispatchers.Main){
                                holder.binding.ayaRcvLayoutTxttafsirContent.setText(it.data.text)
                            }

                        }
                        is Resource.Failure->{
                            Log.e(TAG,"${it.error}")
                            Toast.makeText(context, "فشل الحصول علي التفسير...تاكد من الاتصال بالانترنت", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading->{}
                        else -> {}
                    }
                }
            }
        }


    }
}