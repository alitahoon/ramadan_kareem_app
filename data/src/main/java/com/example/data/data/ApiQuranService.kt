package com.example.data.data

import com.example.domain.entity.quran.QuranResponse
import com.example.domain.entity.quran_audio.QuranAudioResponse
import retrofit2.Response

import retrofit2.http.GET

interface ApiQuranService {


    @GET("quran/ar.alafasy")
    suspend fun getQuran(): Response<QuranResponse>

    @GET("quran/ar.alafasy")
    suspend fun getQuranWithAudio(): Response<QuranAudioResponse>

}