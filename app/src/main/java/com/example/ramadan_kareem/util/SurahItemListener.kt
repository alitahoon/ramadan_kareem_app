package com.example.ramadan_kareem.util

import com.example.domain.entity.quran_audio.Surah

interface SurahItemListener {
    fun onSurahClicked(surah: Surah)

}