package com.example.domain.entity.quran_en

data class Surah(
    val ayahs: List<Ayah>,
    val englishName: String,
    val englishNameTranslation: String,
    val name: String,
    val number: Int,
    val revelationType: String
)