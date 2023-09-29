package com.example.domain.usecase

import Resource
import com.example.domain.entity.quran.QuranResponse
import com.example.domain.entity.quran_en.QuranEnglishResponse
import com.example.domain.repo.UserRepo

class GetEnglishQuran(private val userRepo: UserRepo) {
    suspend operator fun invoke(result: (Resource<QuranEnglishResponse>) -> Unit)=userRepo.getEnglishQuran(result)
}