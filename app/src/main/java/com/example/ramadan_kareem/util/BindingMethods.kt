package com.example.ramadan_kareem.util

import Resource
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.entity.quran_audio.Ayah
import com.example.domain.entity.quran_audio.Surah
import com.example.domain.usecase.GetAyahInEnglish
import com.example.domain.usecase.GetTafsirForAya
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.ui.Surah_ayas_viewer
import com.example.ramadan_kareem.ui.showFullText
import com.ramotion.foldingcell.FoldingCell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import android.widget.ArrayAdapter

class BindingMethods @Inject constructor(
    private val getAyahInEnglish: GetAyahInEnglish,
    private val getTafsirForAya: GetTafsirForAya
):onSettingAya{
    val coroutineScope: CoroutineScope = MainScope()
    init {
        setListener(this)
    }
    companion object{
        val colorArabicSpan = ForegroundColorSpan(Color.GREEN)
        val colorEnglishSpan = ForegroundColorSpan(Color.YELLOW)
        private var moretextArabic=SpannableString("اضغط لقراءة المزيد").apply {
            this.setSpan(colorArabicSpan,0,17,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        private var moretextEnglish=SpannableString("Read more...").apply {
           this.setSpan(colorEnglishSpan,0,12,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        private lateinit var adapter: ArrayAdapter<String>
        private val TAG="bindingMethods"
        private var listener: onSettingAya? = null
        fun setListener(listener: onSettingAya) {
            this.listener = listener
        }
        @BindingAdapter("setSurahAya")
        @JvmStatic
        fun setSurahAya(textView: TextView,surah: Surah){
            var surahAyaTXT:StringBuilder=java.lang.StringBuilder()
            for (aya in surah.ayahs){
                if (aya.sajda){
                    surahAyaTXT.append("${aya.text}  (${aya.numberInSurah}) ")
                }else{
                    surahAyaTXT.append("${aya.text}  (${aya.numberInSurah}) ")
                }
            }
            textView.text=surahAyaTXT
        }



        @BindingAdapter("setSurahAyaNumber")
        @JvmStatic
        fun setSurahAyaNumber(textView: TextView,ayas:List<Ayah>){
            try {
                textView.setText("${ayas.size}"+ " ايات ")

            }catch (e:Exception){
                Log.e(TAG,"${e.stackTrace}")
            }
        }

        @BindingAdapter("setAyaText")
        @JvmStatic
        fun setAyaText(textView: TextView,ayas:Ayah){
            try {
                textView.setText(ayas.text + " ")

            }catch (e:Exception){
                Log.e(TAG,"${e.stackTrace}")
            }
        }

        @BindingAdapter("setFajrAzanTime")
        @JvmStatic
        fun setFajrAzanTime(textView: TextView,azanResponse:AzanResponse){
            //get currant date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            //set azan
            val size=azanResponse.data.size
            textView.setText(azanResponse.data.get(size-1).timings.Fajr)
//    for (azan in azanResponse.data){
//        //set azan data
//        Log.d(TAG,"${azan}")
////        if (azan.date.gregorian.year.toInt()==year&&azan.date.gregorian.month.number==month&&azan.date.gregorian.day.toInt()==day) {
//        }
//    }
        }
        @BindingAdapter("setDhuhrAzanTime")
        @JvmStatic
        fun setDhuhrAzanTime(textView: TextView,azanResponse:AzanResponse){
            //get currant date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            //set azan
            val size=azanResponse.data.size
            textView.setText(azanResponse.data.get(size-1).timings.Dhuhr)
//    for (azan in azanResponse.data){
//        //set azan data
//        Log.d(TAG,"${azan}")
////        if (azan.date.gregorian.year.toInt()==year&&azan.date.gregorian.month.number==month&&azan.date.gregorian.day.toInt()==day) {
//        }
//    }
        }
        @BindingAdapter("setAsrAzanTime")
        @JvmStatic
        fun setAsrAzanTime(textView: TextView,azanResponse:AzanResponse){
            //get currant date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            //set azan
            val size=azanResponse.data.size
            textView.setText(azanResponse.data.get(size-1).timings.Asr)
//    for (azan in azanResponse.data){
//        //set azan data
//        Log.d(TAG,"${azan}")
////        if (azan.date.gregorian.year.toInt()==year&&azan.date.gregorian.month.number==month&&azan.date.gregorian.day.toInt()==day) {
//        }
//    }
        }
        @BindingAdapter("setMaghribAzanTime")
        @JvmStatic
        fun setMaghribAzanTime(textView: TextView,azanResponse:AzanResponse){
            //get currant date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            //set azan
            val size=azanResponse.data.size
            textView.setText(azanResponse.data.get(size-1).timings.Maghrib)
//    for (azan in azanResponse.data){
//        //set azan data
//        Log.d(TAG,"${azan}")
////        if (azan.date.gregorian.year.toInt()==year&&azan.date.gregorian.month.number==month&&azan.date.gregorian.day.toInt()==day) {
//        }
//    }
        }
        @BindingAdapter("setIshaAzanTime")
        @JvmStatic
        fun setIshaAzanTime(textView: TextView,azanResponse:AzanResponse){
            //get currant date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            //set azan
            val size=azanResponse.data.size
            textView.setText(azanResponse.data.get(size-1).timings.Isha)
//    for (azan in azanResponse.data){
//        //set azan data
//        Log.d(TAG,"${azan}")
////        if (azan.date.gregorian.year.toInt()==year&&azan.date.gregorian.month.number==month&&azan.date.gregorian.day.toInt()==day) {
//        }
//    }
        }
        @BindingAdapter("setAyaNumber")
        @JvmStatic
        fun setAyaNumber(textView: TextView,ayas:Ayah){
            try {
                textView.setText(ayas.numberInSurah.toString() + " ")

            }catch (e:Exception){
                Log.e(TAG,"${e.stackTrace}")
            }
        }

        @BindingAdapter("listener","aya")
        @JvmStatic
        fun addToFavourite(lottieAnimationView: LottieAnimationView,listener: AyahItemListener,aya: Ayah){
            lottieAnimationView.setOnClickListener{
                lottieAnimationView.playAnimation()
                listener.onBtnSaveAyahClicked(aya)
            }
        }

        @BindingAdapter("playAyahSound")
        @JvmStatic
        fun playAyahSound(imageView: ImageView,i:Int){
            if (imageView.drawable==imageView.context.getDrawable(R.drawable.baseline_play_circle_filled_24)){
                imageView.setImageDrawable(imageView.context.getDrawable(R.drawable.baseline_stop_circle_24))
            }else{
                imageView.setImageDrawable(imageView.context.getDrawable(R.drawable.baseline_play_circle_filled_24))
            }


        }

        @BindingAdapter("playFoldingCellAnimation")
        @JvmStatic
        fun playFoldingCellAnimation(foldingCell: FoldingCell,i:Int){
            foldingCell.toggle(false)
        }

        @BindingAdapter("translateAya")
        @JvmStatic
        fun setAyaenglishTranslation(textView: TextView,ayaNumber:Int){
            listener!!.setAyaNumber(textView,ayaNumber)
        }
        @BindingAdapter("tafseer_id","sura_number","ayah_number")
        @JvmStatic
        fun setAyaTafseer(textView: TextView, tafseer_id:Int,sura_number: Int, ayah_number: Int){
            listener!!.setAyaTafsir(textView,tafseer_id,sura_number,ayah_number)
        }

        @BindingAdapter("fillTafsirAutoTxt")
        @JvmStatic
        fun fillTafsirAutoTxt(textView: AutoCompleteTextView,i:Int){
           var tafsirArrayOfString=textView.context.resources.getStringArray(R.array.tafsir_title)
            adapter = ArrayAdapter(textView.context,R.layout.drop_down_item,tafsirArrayOfString)
            textView.setAdapter(adapter)
        }
    }

    override fun setAyaNumber(textView: TextView, ayaNumber: Int) {
        coroutineScope.launch (Dispatchers.IO){
            getAyahInEnglish(ayaNumber){
                when(it){
                    is Resource.Success->{
                        Log.d(TAG,"aya in english -> ${it.data}")
                        coroutineScope.launch (Dispatchers.Main){
                            if (it.data.length>300){
                                textView.setText(it.data+ moretextEnglish)
                            }else{
                                textView.setText(it.data)
                            }                        }                    }
                    is Resource.Failure->{
                        Log.e(TAG,"${it.error}")
                    }
                    is Resource.Loading->{}
                    else -> {}
                }
            }
        }
    }

    override fun setAyaTafsir(textView: TextView, tafseer_id:Int,sura_number: Int, ayah_number: Int) {
        coroutineScope.launch (Dispatchers.IO){
            getTafsirForAya(tafseer_id,sura_number,ayah_number){
                when (it){
                    is Resource.Success->{
                        Log.d(TAG,"aya tafsir -> ${it.data}")
                        coroutineScope.launch (Dispatchers.Main){
                            if (it.data.text.length>300){
                                textView.setText(it.data.text+ moretextArabic)
                            }else{
                                textView.setText(it.data.text)
                            }
                        }
                    }
                    is Resource.Failure->{
                        Log.e(TAG,"${it.error}")
                    }
                    is Resource.Loading->{}
                    else -> {}
                }
            }
        }
    }


}

interface onSettingAya{
    fun setAyaNumber(textView: TextView,ayaNumber: Int)
    fun setAyaTafsir( textView: TextView,tafseer_id:Int,sura_number: Int, ayah_number: Int)
}
