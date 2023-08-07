package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class Ayah(
    val audio: String,
    val audioSecondary: List<String>,
    val hizbQuarter: Int,
    val juz: Int,
    val manzil: Int,
    val number: Int,
    val numberInSurah: Int,
    val page: Int,
    val ruku: Int,
    val sajda: Boolean = true , // Default value if 'sajda' is not present in JSON
    val text: String
)