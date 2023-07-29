package com.example.domain.entity.hadith

data class HadithResponse(
    val hadiths: Hadiths,
    val message: String,
    val status: Int
)