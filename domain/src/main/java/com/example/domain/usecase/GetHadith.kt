package com.example.domain.usecase

import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.repo.UserRepo

class GetHadith(private val userRepo: UserRepo) {

    suspend operator fun invoke() = userRepo.getHadithFromRemote()

}