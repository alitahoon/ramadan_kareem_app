package com.example.domain.usecase

import Resource
import com.example.domain.entity.quran.QuranResponse
import com.example.domain.entity.quran_audio.QuranAudioResponse
import com.example.domain.repo.UserRepo

class GetQuranFromRemote (private val userRepo: UserRepo){
    suspend operator fun invoke(result: (Resource<QuranAudioResponse>) -> Unit)=userRepo.getQuranFromRemote(result)
}