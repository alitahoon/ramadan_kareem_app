package com.example.domain.usecase

import com.example.domain.entity.QuranResponse
import com.example.domain.entity.Resource
import com.example.domain.repo.UserRepo

class GetQuranFromRemote (private val userRepo: UserRepo){
    suspend operator fun invoke(result: (Resource<QuranResponse>) -> Unit)=userRepo.getQuranFromRemote(result)
}