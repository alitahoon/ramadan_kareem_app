package com.example.domain.repo

import Resource
import com.example.domain.entity.azkar.AzkarRespons
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.entity.quran.QuranResponse

interface UserRepo {

    suspend fun getHadithFromRemote(result: (Resource<HadithResponse>) -> Unit)
    suspend fun getQuranFromRemote(result: (Resource<QuranResponse>) -> Unit)
    suspend fun getAzkarFromLocal(result: (Resource<List<AzkarRespons>>) -> Unit)

}