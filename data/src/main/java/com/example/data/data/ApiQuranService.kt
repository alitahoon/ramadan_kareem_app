package com.example.data.data

import com.example.domain.entity.quran.QuranResponse
import retrofit2.Response

import retrofit2.http.GET

interface ApiQuranService {


    @GET("quran/ar.alafasy")
    suspend fun getQuran(): Response<QuranResponse>

}