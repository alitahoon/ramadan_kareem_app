package com.example.ramadan_kareem.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.entity.quran.Ayah
import com.example.domain.entity.quran.Surah
import com.example.ramadan_kareem.R
import java.util.Calendar

private val TAG="bindingMethods"

@BindingAdapter("setSurahAya")
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
fun setSurahAyaNumber(textView: TextView,ayas:List<Ayah>){
    try {
        textView.setText("${ayas.size}"+ " ايات ")

    }catch (e:Exception){
        Log.e(TAG,"${e.stackTrace}")
    }
}

@BindingAdapter("setAyaText")
fun setAyaText(textView: TextView,ayas:Ayah){
    try {
        textView.setText(ayas.text + " ")

    }catch (e:Exception){
        Log.e(TAG,"${e.stackTrace}")
    }
}

@BindingAdapter("setFajrAzanTime")
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
fun setAyaNumber(textView: TextView,ayas:Ayah){
    try {
        textView.setText(ayas.numberInSurah.toString() + " ")

    }catch (e:Exception){
        Log.e(TAG,"${e.stackTrace}")
    }
}

@BindingAdapter("addToFavourite")
fun addToFavourite(lottieAnimationView: LottieAnimationView,i:Int){
    lottieAnimationView.setOnClickListener{
        lottieAnimationView.playAnimation()

    }
}

@BindingAdapter("playAyahSound")
fun addToFavourite(imageView: ImageView,i:Int){


}