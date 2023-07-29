package com.example.domain.entity.hadith

data class Data(
    val book: Book,
    val bookSlug: String,
    val chapter: Chapter,
    val chapterId: String,
    val englishNarrator: String,
    val hadithArabic: String,
    val hadithEnglish: String,
    val hadithNumber: String,
    val hadithUrdu: String,
    val id: Int,
    val urduNarrator: String,
    val volume: String
)