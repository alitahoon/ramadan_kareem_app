package com.example.domain.usecase

import Resource
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.repo.UserRepo

class GetHadith(private val userRepo: UserRepo) {

    suspend operator fun invoke(result: (Resource<HadithResponse>) -> Unit) = userRepo.getHadithFromRemote(result)

}