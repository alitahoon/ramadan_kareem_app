package com.example.ramadan_kareem.util

import Resource
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.entity.quran_audio.Ayah
import com.example.domain.entity.quran_audio.Surah
import com.example.domain.usecase.GetAyahInEnglish
import com.example.ramadan_kareem.R
import com.ramotion.foldingcell.FoldingCell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class BindingMethods @Inject constructor(
    private val getAyahInEnglish: GetAyahInEnglish
):onSettingAyatranslation{
    val coroutineScope: CoroutineScope = MainScope()
    init {
        setListener(this)
    }
    companion object{
        private val TAG="bindingMethods"
        private var listener: onSettingAyatranslation? = null
        fun setListener(listener: onSettingAyatranslation) {
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
    }

    override fun setAyaNumber(textView: TextView, ayaNumber: Int) {
        coroutineScope.launch (Dispatchers.Main){
            getAyahInEnglish(ayaNumber){
                when(it){
                    is Resource.Success->{
                        Log.d(TAG,"aya in english -> ${it.data}")
                        textView.setText(it.data)
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

interface onSettingAyatranslation{
    fun setAyaNumber(textView: TextView,ayaNumber: Int)
}
