package com.example.domain.usecase

import Resource
import com.example.domain.repo.UserRepo

class GetAyahInEnglish(private val userRepo: UserRepo) {
    suspend operator fun invoke(ayaNumber: Int,result: (Resource<String>) -> Unit)=userRepo.getAyaInEnglish(ayaNumber,result)
}