package com.example.data.repo

import android.util.Log
import com.example.data.data.ApiService
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.repo.UserRepo

class userRepoImpl(private val apiService: ApiService):UserRepo{
    override suspend fun getHadithFromRemote(): HadithResponse {
        Log.d("mohamedtag","${apiService.getHadith()}")
        return apiService.getHadith()
    }
}