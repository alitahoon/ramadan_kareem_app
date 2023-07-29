package com.example.domain.repo

import com.example.domain.entity.hadith.HadithResponse

interface UserRepo {

    suspend fun getHadithFromRemote():HadithResponse

}