package com.example.domain.usecase

import Resource
import com.example.domain.repo.UserRepo

class GetAyaAudioLinkFromRemote(private val userRepo: UserRepo) {
    suspend operator fun invoke(ayaNumber:Int,result: (Resource<String>) -> Unit)=userRepo.getAyaAudioLinkFromRemote(ayaNumber,result)
}