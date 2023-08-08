package com.example.domain.repo

import com.example.domain.entity.QuranResponse
import com.example.domain.entity.Resource
import com.example.domain.entity.azkar.AzkarRespons
import com.example.domain.entity.hadith.HadithResponse

interface UserRepo {

    suspend fun getHadithFromRemote(result: (Resource<HadithResponse>) -> Unit)
    suspend fun getQuranFromRemote(result: (Resource<QuranResponse>) -> Unit)
    suspend fun getAzkarCategoryFromLocal(result: (Resource<List<AzkarRespons>>) -> Unit)
    suspend fun getAzkarFromLocal(result: (Resource<List<AzkarRespons>>) -> Unit, category:String)

}