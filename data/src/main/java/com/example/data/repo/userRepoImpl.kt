package com.example.data.repo

import android.util.Log
import com.example.data.data.ApiService
import com.example.domain.entity.Resource
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.repo.UserRepo

class userRepoImpl(private val apiService: ApiService):UserRepo{
    private val TAG="userRepoImpl"
    override suspend fun getHadithFromRemote(result: (Resource<HadithResponse>) -> Unit){
        var hadithResponse:HadithResponse?=null
        val response=apiService.getHadith()
        if (response.isSuccessful){
            if (response.body()!=null){
                Log.d(TAG,"${response!!.body()!!.hadiths}")
                result.invoke(Resource.Success(response!!.body()!!))
            }else{
                Log.e(TAG,"data is null ")
                result.invoke(Resource.Failure("failed to get hadieth data is null "))
            }
        }else{
            Log.e(TAG,"failed to get hadieth ${response.body()}")
            result.invoke(Resource.Failure("failed to get hadieth ${response.body()}"))
        }

    }
}