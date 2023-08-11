package com.example.ramadan_kareem.util

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.domain.entity.quran.Surah
import com.example.ramadan_kareem.R

@BindingAdapter("setSurahAya")
fun setSurahAya(textView: TextView, surah: Surah) {
    val spannableBuilder = SpannableStringBuilder()

    for (aya in surah.ayahs) {
        val ayaText = aya.text
        val ayaNumber = aya.numberInSurah.toString()

        val formattedText = if (aya.sajda) {
            "${ayaText} ${ayaNumber} (${aya.numberInSurah})"
        } else {
            "${ayaText} (${ayaNumber})"
        }

        val spannableString = SpannableString(formattedText)

        // Apply color to the aya number part
        val ayaNumberStart = formattedText.indexOf(ayaNumber) - 1
        val ayaNumberEnd = ayaNumberStart + ayaNumber.length + 1
        spannableString.setSpan(
            ForegroundColorSpan(textView.context.resources.getColor(R.color.icon_yello)),
            ayaNumberStart,
            ayaNumberEnd,
            0
        )

        // Append the formatted SpannableString to the builder
        spannableBuilder.append(spannableString)

        // Add a newline between ayahs
        spannableBuilder.append("\n")
    }

    textView.text = spannableBuilder
}
