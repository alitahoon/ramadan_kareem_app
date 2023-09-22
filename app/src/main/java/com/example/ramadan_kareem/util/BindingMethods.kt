package com.example.ramadan_kareem.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.example.domain.entity.quran.Ayah
import com.example.domain.entity.quran.Surah
import com.example.ramadan_kareem.R

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