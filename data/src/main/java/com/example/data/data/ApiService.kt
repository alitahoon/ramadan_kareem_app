package com.example.data.data

import com.example.domain.entity.hadith.HadithResponse
import retrofit2.Response

import retrofit2.http.GET

interface ApiService {

    @GET("hadiths?apiKey=\$2y\$10\$Woi3shlcGWsVP4V1atH9UusYTEAxcPBaMKEuwqnDrScJngXwj1kfW")
    suspend fun getHadith(): Response<HadithResponse>

}