package com.example.data.repo

import android.util.Log
import com.example.data.data.ApiHadithService
import com.example.data.data.ApiQuranService
import com.example.domain.entity.QuranResponse
import com.example.domain.entity.Resource
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.repo.UserRepo

class userRepoImpl(
    private val apiHadithService: ApiHadithService,
    private val apiQuranService: ApiQuranService
) : UserRepo {
    private val TAG = "userRepoImpl"
    override suspend fun getHadithFromRemote(result: (Resource<HadithResponse>) -> Unit) {
        val response = apiHadithService.getHadith()
        if (response.isSuccessful) {
            if (response.body() != null) {
                Log.d(TAG, "${response!!.body()!!.hadiths}")
                result.invoke(Resource.Success(response!!.body()!!))
            } else {
                Log.e(TAG, "data is null ")
                result.invoke(Resource.Failure("failed to get hadieth data is null "))
            }
        } else {
            Log.e(TAG, "failed to get hadieth ${response.body()}")
            result.invoke(Resource.Failure("failed to get hadieth ${response.body()}"))
        }

    }

    override suspend fun getQuranFromRemote(result: (Resource<QuranResponse>) -> Unit) {
        val response = apiQuranService.getQuran()
        if (response.isSuccessful) {
            if (response.body() != null) {
                Log.d(TAG, "${response!!.body()!!.data}")
                result.invoke(Resource.Success(response!!.body()!!))
            } else {
                Log.e(TAG, "data is null ")
                result.invoke(Resource.Failure("failed to get quran data is null "))
            }
        } else {
            Log.e(TAG, "failed to get quran ${response.body()}")
            result.invoke(Resource.Failure("failed to get quran ${response.body()}"))
        }

    }
}