package com.example.ramadan_kareem.util

import com.example.domain.entity.quran.Ayah
import com.example.domain.entity.quran.Surah

interface AyahItemListener {
    fun onBtnPlayAyaClicked(ayah: Ayah)
    fun onBtnCopyAyahClicked(ayah: Ayah)
    fun onBtnShareAyaClicked(ayah: Ayah)
    fun onBtnSaveAyahClicked(ayah: Ayah)
}